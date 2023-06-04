package pl.nqriver.users;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
@Path("/managers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreweryManagerResource {

    @Inject
    BreweryManagerFacade managerFacade;

    @POST
    @Path("/register")
    @Transactional
    public BreweryManagerResponse register(ManagerRegistrationRequest request) {
        return  managerFacade.registerNewBreweryManager(request);
    }


    @POST
    @Path("/login")
    public JwtTokenResponse login(ManagerLoginRequest request) {
        String jwt = managerFacade.login(request);
        return new JwtTokenResponse(jwt);
    }

    public record BreweryManagerResponse(UUID id, String name, String login, String email, String phoneNumber,
                                         Instant hireDate, UUID managedBreweryId) {

    }


    public record ManagerLoginRequest(String login, String password) {

    }

    public record JwtTokenResponse(String jwt) {
    }


    public record ManagerRegistrationRequest(String name, String login, String password, String email,
                                             String phoneNumber, UUID managedBreweryId) {
    }

}
