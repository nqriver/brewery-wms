package pl.nqriver.brewery.api;

import io.quarkus.security.Authenticated;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import pl.nqriver.beer.api.BeerResource.BeerResponse;
import pl.nqriver.brewery.domain.BreweryCrudFacade;
import pl.nqriver.commons.LoggedInUserService;

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
    public List<BreweryResponse> getBreweries() {
        return breweryCrudFacade.getAll();
    }


    @GET
    @Path("{breweryId}/beers")
    public List<BeerResponse> getProducedBeers(@PathParam("breweryId") UUID breweryId) {
        if (!loggedInUserService.checkBreweryPermissions(breweryId)) {
            throw new UnauthorizedException();
        }
        return breweryCrudFacade.getProducedBeers(breweryId);
    }

    @POST
    @Path("{breweryId}/images")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadBreweryImages(@PathParam("breweryId") UUID breweryId, List<Image> images) {
        if (!loggedInUserService.checkBreweryPermissions(breweryId)) {
            throw new UnauthorizedException();
        }

        if (images == null || images.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<FileUpload> uploadedFiles = images.stream().map(Image::fileUpload).toList();
        BreweryResponse brewery = breweryCrudFacade.addBreweryImages(breweryId, uploadedFiles);
        return Response.status(Response.Status.CREATED).entity(brewery).build();
    }

    public record BreweryCreateRequest(String name, String city, String postalCode, int surfaceArea) {
    }

    public record BreweryResponse(UUID id, String name, String city, String postalCode, int surfaceArea) {
    }

    public record Image(
            @Schema(type = SchemaType.STRING, format = "binary")
            FileUpload fileUpload
    ) {
    }

}
