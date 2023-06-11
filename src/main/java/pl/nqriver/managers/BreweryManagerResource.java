package pl.nqriver.managers;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.nqriver.brewery.api.BreweryResource;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("/managers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreweryManagerResource {

    @Inject
    BreweryManagerFacade managerFacade;


    @PUT
    @Path("/{managerId}")
    public BreweryManagerResponse editManager(@PathParam("managerId") UUID managerId, ManagerEditRequest editRequest) {
        return managerFacade.edit(managerId, editRequest);
    }


    @GET
    @Path("/{managerId}/managed-breweries")
    @Authenticated
    public List<BreweryResource.BreweryResponse> getManagedBreweries(@PathParam("managerId") UUID managerId) {
        return managerFacade.getManagedBreweries(managerId);
    }

    @DELETE
    @Path("/{managerId}/managed-breweries/{breweryId}")
    @Authenticated
    public Response revokePermissionForBrewery(@PathParam("managerId") UUID managerId, @PathParam("breweryId") UUID breweryId) {
        managerFacade.removePermissionForBrewery(managerId, breweryId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{managerId}/managed-breweries")
    @Authenticated
    public Response revokeAllPermissions(@PathParam("managerId") UUID managerId) {
        managerFacade.removeAllPermissions(managerId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{managerId}")
    @Authenticated
    public BreweryManagerResponse getManager(@PathParam("managerId") UUID managerId) {
        return managerFacade.get(managerId);
    }

    @POST
    @Path("/register")
    @Transactional
    public BreweryManagerCreateResponse register(ManagerRegistrationRequest request) {
        return managerFacade.registerNewBreweryManager(request);
    }


    @POST
    @Path("/login")
    public JwtTokenResponse login(ManagerLoginRequest request) {
        String jwt = managerFacade.login(request);
        return new JwtTokenResponse(jwt);
    }

    public record BreweryManagerResponse(UUID id, String name, String login, String email, String phoneNumber,
                                         Instant hireDate) {

    }

    public record BreweryManagerCreateResponse(BreweryManagerResponse manager, JwtTokenResponse jwt) {

    }


    public record ManagerLoginRequest(String login, String password) {

    }

    public record ManagerEditRequest(String name, String login, String email, String phoneNumber,
                                     Instant hireDate) {

    }

    public record JwtTokenResponse(String jwt) {
    }


    public record ManagerRegistrationRequest(String name, String login, String password, String email,
                                             String phoneNumber, UUID managedBreweryId) {
    }

}
