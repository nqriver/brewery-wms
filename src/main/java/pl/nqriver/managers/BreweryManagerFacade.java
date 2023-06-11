package pl.nqriver.managers;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.nqriver.brewery.api.BreweryResource;
import pl.nqriver.brewery.domain.Brewery;
import pl.nqriver.brewery.domain.BreweryFacade;
import pl.nqriver.commons.ServiceErrorCode;
import pl.nqriver.commons.ServiceException;
import pl.nqriver.managers.api.BreweryManagerResource.*;
import pl.nqriver.managers.domain.BreweryManager;
import pl.nqriver.managers.domain.BreweryManagerRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BreweryManagerFacade {

    @Inject
    BreweryManagerRepository managerRepository;

    @Inject
    BreweryFacade breweryFacade;

    @Inject
    JwtService jwtService;


    @Transactional
    public BreweryManagerCreateResponse registerNewBreweryManager(ManagerRegistrationRequest registrationRequest) {
        if (managerRepository.findByLogin(registrationRequest.login()).isPresent()) {
            throw new ServiceException(ServiceErrorCode.MANAGER_LOGIN_TAKEN);
        } else if (managerRepository.findByEmail(registrationRequest.email()).isPresent()) {
            throw new ServiceException(ServiceErrorCode.MANAGER_EMAIL_TAKEN);
        }

        List<Brewery> breweries = breweryFacade.findAll();

        BreweryManager manager = BreweryManager.fromRequest(registrationRequest, breweries);
        managerRepository.persist(manager);
        JwtTokenResponse jwt = new JwtTokenResponse(jwtService.getJwt(manager));
        return new BreweryManagerCreateResponse(manager.toResponse(), jwt);
    }


    public String login(ManagerLoginRequest loginRequest) {
        BreweryManager manager = managerRepository.findByLogin(loginRequest.login())
                .orElseThrow(UnauthorizedException::new);

        if (!BcryptUtil.matches(loginRequest.password(), manager.getPassword())) {
            throw new UnauthorizedException();
        }

        return jwtService.getJwt(manager);
    }

    public List<BreweryResource.BreweryResponse> getManagedBreweries(UUID managerId) {
        BreweryManager manager = findOne(managerId);
        return manager.getManagedBreweries().stream().map(Brewery::toResponse).toList();
    }

    @Transactional
    public void removePermissionForBrewery(UUID managerId, UUID breweryId) {
        BreweryManager manager = findOne(managerId);
        Brewery brewery = manager.getManagedBreweries()
                .stream()
                .filter(e -> e.getId().equals(breweryId))
                .findFirst().orElseThrow(() -> new ServiceException(ServiceErrorCode.INVALID_BREWERY));
        manager.removePermissionForBrewery(brewery);
        managerRepository.persist(manager);

    }

    @Transactional
    public void removeAllPermissions(UUID managerId) {
        BreweryManager manager = findOne(managerId);
        manager.removeAllPermissions();
        managerRepository.persist(manager);
    }

    public BreweryManagerResponse get(UUID managerId) {
        return findOne(managerId)
                .toResponse();
    }

    private BreweryManager findOne(UUID managerId) {
        return managerRepository.findByIdOptional(managerId).orElseThrow(() -> new ServiceException(ServiceErrorCode.MANAGER_NOT_FOUND));
    }

    @Transactional
    public BreweryManagerResponse edit(UUID managerId, ManagerEditRequest editRequest) {
        BreweryManager manager = findOne(managerId);

        if (editRequest.email() != null) {
            assertEmailIsNotUsed(editRequest.email(), managerId);
            manager.setEmail(editRequest.email());
        }
        if (editRequest.phoneNumber() != null) {
            manager.setPhoneNumber(editRequest.phoneNumber());
        }
        if (editRequest.login() != null) {
            assertLoginIsNotUsed(editRequest.login(), managerId);
            manager.setLogin(editRequest.login());
        }
        if (editRequest.name() != null) {
            manager.setName(editRequest.name());
        }
        managerRepository.persist(manager);
        return manager.toResponse();
    }

    private void assertEmailIsNotUsed(String email, UUID managerId) {
        managerRepository.find("email", email).firstResultOptional().ifPresent(found -> {
            if (!managerId.equals(found.getId())) {
                throw new ServiceException(ServiceErrorCode.MANAGER_EMAIL_TAKEN);
            }
        });
    }

    private void assertLoginIsNotUsed(String login, UUID managerId) {
        managerRepository.find("login", login).firstResultOptional().ifPresent(found -> {
            if (!managerId.equals(found.getId())) {
                throw new ServiceException(ServiceErrorCode.MANAGER_LOGIN_TAKEN);
            }
        });
    }
}
