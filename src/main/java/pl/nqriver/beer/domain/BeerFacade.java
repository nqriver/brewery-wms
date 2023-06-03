package pl.nqriver.beer.domain;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import pl.nqriver.beer.api.BeerResource;

import java.util.List;

@ApplicationScoped
public class BeerFacade {


    @Inject
    BeerRepository beerRepository;

    @Inject
    BeerStyleRepository beerStyleRepository;


    @Transactional
    public Beer addNewBeer(BeerResource.CreateBeerRequest createBeerRequest) {
        Beer beer = beerStyleRepository.findByIdOptional(createBeerRequest.beerStyleId())
                .map(style -> Beer.fromRequest(createBeerRequest, style))
                .orElseThrow(() -> new BadRequestException("Cannot find specified beer style"));

        beerRepository.persist(beer);
        return beer;
    }

    public List<BeerResource.BeerResponse> listAll() {
        return beerRepository.findAll(Sort.ascending("name"))
                .stream()
                .map(Beer::toResponse)
                .toList();
    }

    public List<BeerResource.BeerResponse> getBeersByStyle(Long id) {
        return beerRepository.find("style.id", id)
                .stream()
                .map(Beer::toResponse)
                .toList();
    }
}
