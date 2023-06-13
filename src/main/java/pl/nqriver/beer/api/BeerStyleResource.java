package pl.nqriver.beer.api;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.nqriver.beer.api.BeerResource.BeerResponse;
import pl.nqriver.beer.BeerFacade;

import java.util.List;

@Path("/beer-styles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Authenticated
public class BeerStyleResource {
    @Inject
    BeerFacade beerFacade;

    @GET
    public List<BeerStyleDto> getBeerStyles() {
        return beerFacade.findAllBeerStyles();
    }

    @GET
    @Path("/{id}/beers")
    public List<BeerResponse> getBeersByStyle(@PathParam("id") Long id) {
        return beerFacade.getBeersByStyle(id);
    }

    @GET
    @Path("/{id}")
    public BeerStyleDto getBeerStyle(@PathParam("id") Long id) {
        return beerFacade.findBeerStyle(id);
    }

    public record BeerStyleDto(Long id, String name) {
    }

}

