package pl.nqriver.beer.api;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.nqriver.beer.domain.BeerFacade;
import pl.nqriver.beer.domain.BeerStyle;
import pl.nqriver.beer.domain.BeerStyleRepository;

import java.util.List;

@Path("/beer-styles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Authenticated
public class BeerStyleResource {

    @Inject
    BeerStyleRepository beerStyleRepository;
    @Inject
    BeerFacade beerFacade;

    @GET
    public List<BeerStyle> getBeerStyles() {
        return beerStyleRepository.listAll();
    }

    @GET
    @Path("/{id}/beers")
    public List<BeerResource.BeerResponse> getBeersByStyle(@PathParam("id") Long id) {
        return beerFacade.getBeersByStyle(id);
    }


    @POST
    @Transactional
    public BeerStyle addBeerStyle(BeerStyle beerStyle) {
        beerStyleRepository.persist(beerStyle);
        return beerStyle;
    }

    @GET
    @Path("/{id}")
    public BeerStyle getBeerStyle(@PathParam("id") Long id) {
        return beerStyleRepository.findById(id);
    }
}

