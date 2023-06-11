package pl.nqriver.beer.domain;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import pl.nqriver.beer.api.BeerResource;
import pl.nqriver.beer.api.BeerResource.BeerResponse;

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
                .orElseThrow(() -> new BadRequestException("Cannot find specified beer style",
                        Response.status(400, "Cannot find specified beer style").build()));

        beerRepository.persist(beer);
        return beer.toResponse();
    }

    public List<BeerResponse> listAll() {
        return beerRepository.findAll(Sort.ascending("name"))
                .stream()
                .map(Beer::toResponse)
                .toList();
    }

    public List<BeerResponse> getBeersByStyle(Long id) {
        return beerRepository.find("style.id", id)
                .stream()
                .map(Beer::toResponse)
                .toList();
    }

    public BeerResponse findById(UUID id) {
        return beerRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Beer of given id does not exist"))
                .toResponse();
    }

    public Beer find(UUID beerId) {
        return beerRepository.findByIdOptional(beerId).orElseThrow(NotFoundException::new);
    }
}
