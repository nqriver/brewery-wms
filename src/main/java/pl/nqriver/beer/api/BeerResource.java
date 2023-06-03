package pl.nqriver.beer.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.nqriver.beer.domain.BeerFacade;

import java.util.List;
import java.util.UUID;

@Path("/beers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BeerResource {

    @Inject
    BeerFacade beerFacade;

    @GET
    public List<BeerResponse> getBeers() {
        return beerFacade.listAll();
    }

    @POST
    public BeerResponse addBeer(CreateBeerRequest createBeerRequest) {
        return beerFacade.addNewBeer(createBeerRequest);
    }

    @GET
    @Path("/{id}")
    public BeerResponse getBeer(@PathParam("id") UUID id) {
        return beerFacade.findById(id);
    }

    public record CreateBeerRequest(
            String name,
            Long beerStyleId,
            double ibu,
            double bottleCapacity,
            double alcoholPercentage,
            String color,
            String description,
            double price
    ) {
    }

    public record BeerResponse(
            UUID id,
            String name,
            String beerStyleName,
            Long beerStyleId,
            double ibu,
            double bottleCapacity,
            double alcoholPercentage,
            String color,
            String description,
            double price

    ) {
    }


}

