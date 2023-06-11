package pl.nqriver.managers;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BreweryManagerRepository implements PanacheRepositoryBase<BreweryManager, UUID> {
    public Optional<BreweryManager> findByLogin(String login) {
        return find("login", login).firstResultOptional();
    }

    public Optional<BreweryManager> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
