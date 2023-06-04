package pl.nqriver.users;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import pl.nqriver.brewery.domain.Brewery;
import pl.nqriver.brewery.domain.BreweryFacade;
import pl.nqriver.users.BreweryManagerResource.ManagerLoginRequest;
import pl.nqriver.users.BreweryManagerResource.ManagerRegistrationRequest;

import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class BreweryManagerFacade {

    @Inject
    BreweryManagerRepository managerRepository;

    @Inject
    BreweryFacade breweryFacade;

    @Inject
    JwtService jwtService;


    public boolean isBreweryManagedByManager(UUID managerId, UUID breweryId) {
        return managerRepository.findByIdOptional(managerId)
                .map(manager -> Objects.equals(manager.getManagedBrewery().getId(), breweryId))
                .orElse(false);
    }

    @Transactional
    public BreweryManagerResource.BreweryManagerResponse registerNewBreweryManager(ManagerRegistrationRequest registrationRequest) {
        if (managerRepository.findByLogin(registrationRequest.login()).isPresent()) {
            throw new BadRequestException("Login already taken");
        } else if (managerRepository.findByEmail(registrationRequest.email()).isPresent()) {
            throw new BadRequestException("Email already taken");
        }

        Brewery brewery = breweryFacade.findByIdOptional(registrationRequest.managedBreweryId())
                .orElseThrow(() -> new BadRequestException("Cannot find managed brewery"));

        BreweryManager manager = BreweryManager.fromRequest(registrationRequest, brewery);
        managerRepository.persist(manager);
        return manager.toResponse();
    }


    public String login(ManagerLoginRequest loginRequest) {
        BreweryManager manager = managerRepository.findByLogin(loginRequest.login())
                .orElseThrow(UnauthorizedException::new);

        if (!BcryptUtil.matches(loginRequest.password(), manager.getPassword())) {
            throw new UnauthorizedException();
        }

        return jwtService.getJwt(manager);
    }

}
