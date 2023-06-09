package pl.nqriver.brewery.domain;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import pl.nqriver.beer.api.BeerResource.BeerResponse;
import pl.nqriver.beer.domain.Beer;
import pl.nqriver.beer.BeerFacade;
import pl.nqriver.brewery.api.BreweryResource;
import pl.nqriver.brewery.api.BreweryResource.BreweryDetailedResponse;
import pl.nqriver.brewery.api.BreweryResource.BreweryResponse;
import pl.nqriver.commons.ServiceErrorCode;
import pl.nqriver.commons.ServiceException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BreweryCrudFacade {

    @Inject
    BreweryRepository breweryRepository;

    @Inject
    BeerFacade beerFacade;


    @Transactional
    public BreweryResponse addBreweryImages(UUID breweryId, List<FileUpload> uploadedFiles) {
        Brewery brewery = findOne(breweryId);

        uploadedFiles.stream()
                .map(this::convertImgToByteArray)
                .filter(Optional::isPresent)
                .forEach(image -> brewery.addImage(image.get()));

        breweryRepository.persist(brewery);
        return brewery.toResponse();
    }

    @Transactional
    public BreweryResponse create(BreweryResource.BreweryCreateRequest breweryCreateRequest) {
        Brewery brewery = Brewery.fromRequest(breweryCreateRequest, Collections.emptyList());
        breweryRepository.persist(brewery);
        return brewery.toResponse();

    }

    public List<BreweryResponse> getAll() {
        return breweryRepository.listAll(Sort.ascending("name"))
                .stream()
                .map(Brewery::toResponse)
                .toList();
    }

    public BreweryDetailedResponse get(UUID id) {
        Brewery brewery = findOne(id);

        List<BeerResponse> producedBeers = brewery.getProducedBeers().stream().map(Beer::toResponse).toList();
        return new BreweryDetailedResponse(brewery.toResponse(), producedBeers);
    }

    public List<BeerResponse> getProducedBeers(UUID breweryId) {
        return findOne(breweryId)
                .getProducedBeers()
                .stream()
                .map(Beer::toResponse)
                .toList();
    }

    private Optional<byte[]> convertImgToByteArray(FileUpload image) {
        try {
            return Optional.of(Files.readAllBytes(image.filePath()));
        } catch (IOException ex) {
            Log.error("Error occurred trying to read uploaded image: {}", ex);
            return Optional.empty();
        }
    }

    @Transactional
    public BeerResponse addProducedBeer(UUID breweryId, UUID beerId) {
        Brewery brewery = findOne(breweryId);
        Beer beer = beerFacade.find(beerId);

        if (brewery.getProducedBeers().contains(beer)) {
            throw new ServiceException(ServiceErrorCode.BEER_ALREADY_PRODUCED);
        }
        brewery.addBeer(beer);
        breweryRepository.persist(brewery);
        return beer.toResponse();
    }

    @Transactional
    public void closeBeerProduction(UUID breweryId, UUID beerId) {
        Brewery brewery = findOne(breweryId);
        Beer beer = beerFacade.find(beerId);
        if (!brewery.producesBeer(beer)) {
            throw new ServiceException(ServiceErrorCode.BEER_IS_NOT_PRODUCED);
        }
        brewery.closeProduction(beer);
        breweryRepository.persist(brewery);
    }

    private Brewery findOne(UUID breweryId) {
        return breweryRepository.findByIdOptional(breweryId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.BREWERY_NOT_FOUND));
    }


    @Transactional
    public BreweryResponse edit(UUID breweryId, BreweryResource.BreweryEditRequest editRequest) {
        Brewery brewery = findOne(breweryId);

        if (editRequest.city() != null) {
            brewery.setCity(editRequest.city());
        }
        if (editRequest.name() != null) {
            brewery.setName(editRequest.name());
        }
        if (editRequest.postalCode() != null) {
            brewery.setPostalCode(editRequest.postalCode());
        }
        if (editRequest.surfaceArea() > 0) {
            brewery.setSurfaceArea(editRequest.surfaceArea());
        }
        breweryRepository.persist(brewery);
        return brewery.toResponse();
    }
}
