package pl.nqriver.brewery.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import pl.nqriver.brewery.domain.BreweryCrudFacade;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("/breweries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreweryResource {

    @Inject
    BreweryCrudFacade breweryCrudFacade;


    @POST
    public Response createBrewery(BreweryCreateRequest request) {
        BreweryResponse brewery = breweryCrudFacade.create(request);
        return Response.status(Response.Status.CREATED).entity(brewery).build();
    }

    @GET
    @Path("")
    public Response getBreweries() {
        List<BreweryResponse> breweries = breweryCrudFacade.getAll();
        return Response.status(Response.Status.OK).entity(breweries).build();
    }

    @POST
    @Path("{breweryId}/images")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

    public Response uploadBreweryImages(@PathParam("breweryId") UUID breweryId, List<Image> images) {
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
