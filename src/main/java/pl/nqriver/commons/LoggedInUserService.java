package pl.nqriver.commons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pl.nqriver.managers.BreweryManagerFacade;

import java.util.UUID;

@ApplicationScoped
public class LoggedInUserService {

    @Inject
    JsonWebToken jwt;

    @Inject
    BreweryManagerFacade managerFacade;


    private boolean checkBreweryPermissions(UUID breweryId) {
        UUID managerId = UUID.fromString(jwt.getClaim("managerId"));
        return managerFacade.isBreweryManagedByManager(managerId, breweryId);
    }

    public void authorizeLoggedInManagerForBrewery(UUID brewery) {
        if (!checkBreweryPermissions(brewery)) {
            throw new ServiceException(ServiceErrorCode.BREWERY_ACCESS_FORBIDDEN);
        }
    }
}
