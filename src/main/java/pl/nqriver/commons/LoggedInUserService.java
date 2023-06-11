package pl.nqriver.commons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class LoggedInUserService {

    @Inject
    JsonWebToken jwt;

    public boolean checkBreweryPermissions(UUID breweryId) {
        Set<String> managedBreweryIdsOfLoggedInUser = jwt.getGroups();
        return managedBreweryIdsOfLoggedInUser.contains(breweryId.toString());
    }
}
