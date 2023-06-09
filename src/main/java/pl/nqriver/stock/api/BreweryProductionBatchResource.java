package pl.nqriver.stock.api;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.nqriver.commons.LoggedInUserService;
import pl.nqriver.stock.domain.BreweryProductionBatch;
import pl.nqriver.stock.domain.BreweryProductionBatchDao;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("stock")
@Authenticated
public class BreweryProductionBatchResource {

    @Inject
    BreweryProductionBatchDao dao;

    @Inject
    LoggedInUserService loggedInUserService;

    @GET
    @Path("batches/{id}")
    public BreweryProductionBatch get(@PathParam("id") UUID id) {
        return dao.findById(id);
    }

    @GET
    @Path("batches")
    public List<BreweryProductionBatch> getAll() {
        return dao.findAll().all();
    }


    @DELETE
    @Path("batches/{id}")
    public void delete(@PathParam("id") UUID id) {
        BreweryProductionBatch breweryProductionBatch = dao.findById(id);
        if (breweryProductionBatch != null) {
            dao.delete(breweryProductionBatch);
        }
    }

    @Path("/breweries/{breweryId}/batches")
    @GET
    public List<BreweryProductionBatch> getByBreweryId(@PathParam("breweryId") UUID breweryId) {
        loggedInUserService.authorizeLoggedInManagerForBrewery(breweryId);
        return dao.findByBreweryId(breweryId).all();
    }

    @Path("/beers/{beerId}/batches")
    @GET
    public List<BreweryProductionBatch> getByBeerId(@PathParam("beerId") UUID beerId) {
        return dao.findByBeerId(beerId).all();
    }

}
