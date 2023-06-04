package pl.nqriver.commons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pl.nqriver.users.BreweryManagerFacade;

import java.util.UUID;

@ApplicationScoped
public class LoggedInUserService {

    @Inject
    JsonWebToken jwt;

    @Inject
    BreweryManagerFacade managerFacade;

    public boolean checkBreweryPermissions(UUID breweryId) {
        UUID managerId = UUID.fromString(jwt.getName());
        return managerFacade.isBreweryManagedByManager(managerId, breweryId);
    }
}
