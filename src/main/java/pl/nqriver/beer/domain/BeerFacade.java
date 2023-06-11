package pl.nqriver.beer.domain;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.nqriver.beer.api.BeerResource;
import pl.nqriver.beer.api.BeerResource.BeerResponse;
import pl.nqriver.commons.ServiceErrorCode;
import pl.nqriver.commons.ServiceException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BeerFacade {


    @Inject
    BeerRepository beerRepository;

    @Inject
    BeerStyleRepository beerStyleRepository;


    @Transactional
    public BeerResponse addNewBeer(BeerResource.CreateBeerRequest createBeerRequest) {
        Beer beer = beerStyleRepository.findByIdOptional(createBeerRequest.beerStyleId())
                .map(style -> Beer.fromRequest(createBeerRequest, style))
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.BEER_STYLE_NOT_FOUND));

        beerRepository.persist(beer);
        return beer.toResponse();
    }

    public List<BeerResponse> listAll() {
        return beerRepository.findAll(Sort.ascending("name"))
                .stream()
                .map(Beer::toResponse)
                .toList();
    }

    public void delete(UUID beerId) {
        boolean deleted = beerRepository.deleteById(beerId);
        if (!deleted) {
            throw new ServiceException(ServiceErrorCode.BEER_NOT_FOUND);
        }
    }

    public List<BeerResponse> getBeersByStyle(Long styleId) {
        return beerRepository.find("style.id", styleId)
                .stream()
                .map(Beer::toResponse)
                .toList();
    }

    public BeerResponse findById(UUID id) {
        return findOne(id)
                .toResponse();
    }

    public Beer find(UUID beerId) {
        return findOne(beerId);
    }

    private Beer findOne(UUID id) {
        return beerRepository.findByIdOptional(id).orElseThrow(() -> new ServiceException(ServiceErrorCode.BEER_NOT_FOUND));
    }

    public List<Beer> getAll() {
        return beerRepository.listAll();
    }
}
