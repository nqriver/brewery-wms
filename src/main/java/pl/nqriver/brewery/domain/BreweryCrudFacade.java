package pl.nqriver.brewery.domain;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import pl.nqriver.brewery.api.BreweryResource;
import pl.nqriver.brewery.api.BreweryResource.BreweryResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BreweryCrudFacade {

    @Inject
    BreweryRepository breweryRepository;


    @Transactional
    public BreweryResponse addBreweryImages(UUID breweryId, List<FileUpload> uploadedFiles) {
        Brewery brewery = breweryRepository.findByIdOptional(breweryId).orElseThrow(NotFoundException::new);

        uploadedFiles.stream()
                .map(this::convertImgToByteArray)
                .filter(Optional::isPresent)
                .forEach(image -> brewery.addImage(image.get()));

        breweryRepository.persist(brewery);
        return brewery.toResponse();
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
    public BreweryResponse create(BreweryResource.BreweryCreateRequest breweryCreateRequest) {

        Brewery brewery = new Brewery();
        brewery.setName(breweryCreateRequest.name());
        brewery.setCity(breweryCreateRequest.city());
        brewery.setPostalCode(breweryCreateRequest.postalCode());
        brewery.setSurfaceArea(breweryCreateRequest.surfaceArea());

        breweryRepository.persist(brewery);
        return brewery.toResponse();

    }

    public List<BreweryResponse> getAll() {
        return breweryRepository.listAll(Sort.ascending("name"))
                .stream()
                .map(Brewery::toResponse)
                .toList();
    }
}
