package pl.nqriver.users;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@ApplicationScoped
public class JwtService {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String jwtIssuer;

    public String getJwt(BreweryManager manager) {
        Instant now = Instant.now();
        Set<String> groups = Set.of(manager.getManagedBrewery().getInternalCode());


        return Jwt.issuer(jwtIssuer)
                .upn(manager.getLogin())
                .groups(groups)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("breweryId", manager.getManagedBrewery() == null ? "" : manager.getManagedBrewery().getId())
                .claim("managerId", manager.getId())
                .sign();
    }
}
