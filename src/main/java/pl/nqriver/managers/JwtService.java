package pl.nqriver.managers;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.nqriver.brewery.domain.Brewery;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class JwtService {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String jwtIssuer;

    public String getJwt(BreweryManager manager) {
        Instant now = Instant.now();
        Set<Brewery> managedBreweries = manager.getManagedBreweries();
        Set<String> managedBreweriesIds = managedBreweries.stream().map(Brewery::getId).map(UUID::toString).collect(Collectors.toSet());

        return Jwt.issuer(jwtIssuer)
                .upn(manager.getLogin())
                .groups(managedBreweriesIds)
                .issuedAt(now)
                .expiresAt(now.plus(4, ChronoUnit.HOURS))
                .claim("breweryIds", managedBreweriesIds)
                .claim("managerId", manager.getId())
                .sign();
    }
}
