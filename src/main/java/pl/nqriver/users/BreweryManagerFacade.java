package pl.nqriver.users;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import pl.nqriver.brewery.domain.Brewery;
import pl.nqriver.brewery.domain.BreweryFacade;
import pl.nqriver.users.BreweryManagerResource.ManagerRegistrationRequest;
import pl.nqriver.users.BreweryManagerResource.ManagerLoginRequest;

import java.time.Instant;

@ApplicationScoped
public class BreweryManagerFacade {

    @Inject
    BreweryManagerRepository managerRepository;

    @Inject
    BreweryFacade breweryFacade;

    @Inject
    JwtService jwtService;

    @Transactional
    public BreweryManager registerNewBreweryManager(ManagerRegistrationRequest registrationRequest) {
        Brewery brewery = breweryFacade.findByIdOptional(registrationRequest.managedBreweryId())
                .orElseThrow(() -> new BadRequestException("Cannot find managed brewery"));

        BreweryManager manager = new BreweryManager();
        manager.setManagedBrewery(brewery);
        manager.setName(registrationRequest.name());
        manager.setLogin(registrationRequest.login());
        manager.setEmail(registrationRequest.email());
        manager.setPassword(BcryptUtil.bcryptHash(registrationRequest.password()));
        manager.setPhoneNumber(registrationRequest.phoneNumber());
        manager.setHireDate(Instant.now());
        managerRepository.persist(manager);
        return manager;
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
