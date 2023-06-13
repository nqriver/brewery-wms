package pl.nqriver.brewery.api;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import pl.nqriver.beer.api.BeerResource.BeerResponse;
import pl.nqriver.brewery.domain.BreweryCrudFacade;
import pl.nqriver.commons.LoggedInUserService;
import pl.nqriver.commons.ServiceErrorCode;
import pl.nqriver.commons.ServiceException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("/breweries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class BreweryResource {

    @Inject
    BreweryCrudFacade breweryCrudFacade;


    @Inject
    LoggedInUserService loggedInUserService;

    @POST
    public BreweryResponse createBrewery(BreweryCreateRequest request) {
        return breweryCrudFacade.create(request);
    }

    @GET
    @PermitAll
    public List<BreweryResponse> getBreweries() {
        return breweryCrudFacade.getAll();
    }


    @GET
    @Path("{breweryId}/beers")
    public List<BeerResponse> getProducedBeers(@PathParam("breweryId") UUID breweryId) {
        loggedInUserService.authorizeLoggedInManagerForBrewery(breweryId);
        return breweryCrudFacade.getProducedBeers(breweryId);
    }

    @POST
    @Path("{breweryId}/beers/{beerId}")
    public BeerResponse addProducedBeer(@PathParam("breweryId") UUID breweryId, @PathParam("beerId") UUID beerId) {
        loggedInUserService.authorizeLoggedInManagerForBrewery(breweryId);
        return breweryCrudFacade.addProducedBeer(breweryId, beerId);
    }

    @DELETE
    @Path("{breweryId}/beers/{beerId}")
    public Response finishBeerProduction(@PathParam("breweryId") UUID breweryId, @PathParam("beerId") UUID beerId) {
        loggedInUserService.authorizeLoggedInManagerForBrewery(breweryId);
        breweryCrudFacade.closeBeerProduction(breweryId, beerId);
        return Response.noContent().build();
    }


    @PUT
    @Path("{breweryId}")
    public BreweryResponse edit(@PathParam("breweryId") UUID breweryId, BreweryEditRequest editRequest) {
        return breweryCrudFacade.edit(breweryId, editRequest);
    }


    @GET
    @Path("{breweryId}")
    public BreweryDetailedResponse get(@PathParam("breweryId") UUID breweryId) {
        loggedInUserService.authorizeLoggedInManagerForBrewery(breweryId);
        return breweryCrudFacade.get(breweryId);
    }

    @POST
    @Path("{breweryId}/images")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadBreweryImages(@PathParam("breweryId") UUID breweryId, List<Image> images) {
        loggedInUserService.authorizeLoggedInManagerForBrewery(breweryId);

        if (images == null || images.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<FileUpload> uploadedFiles = images.stream().map(Image::fileUpload).toList();
        BreweryResponse brewery = breweryCrudFacade.addBreweryImages(breweryId, uploadedFiles);
        return Response.status(Response.Status.CREATED).entity(brewery).build();
    }

    public record BreweryDetailedResponse(BreweryResponse brewery, List<BeerResponse> producedBeers) {
    }


    public record BreweryCreateRequest(String name, String city, String postalCode, int surfaceArea,
                                       String internalCode, List<UUID> producedBeers) {
    }

    public record BreweryEditRequest(String name, String city, String postalCode, int surfaceArea) {
    }

    public record BreweryResponse(UUID id, String name, String city, String postalCode, int surfaceArea) {
    }

    public record Image(
            @FormParam("file")
            FileUpload fileUpload
    ) {
    }

}
