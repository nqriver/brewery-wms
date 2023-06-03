package pl.nqriver.stock;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/breweries/batches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreweryProductionBatchController {

    @Inject
    BreweryProductionBatchDao dao;

    @GET
    @Path("/{id}")
    public BreweryProductionBatch get(@PathParam("id") UUID id) {
        return dao.findById(id);
    }

    @POST
    public void create(BreweryProductionBatch breweryProductionBatch) {
        dao.save(breweryProductionBatch);
    }
    @GET
    public List<BreweryProductionBatch> getAll() {
        return dao.findAll().all();
    }

    @PUT
    public void update(BreweryProductionBatch breweryProductionBatch) {
        dao.update(breweryProductionBatch);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        BreweryProductionBatch breweryProductionBatch = dao.findById(id);
        if (breweryProductionBatch != null) {
            dao.delete(breweryProductionBatch);
        }
    }
}
