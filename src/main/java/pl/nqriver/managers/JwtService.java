package pl.nqriver.managers;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.nqriver.managers.domain.BreweryManager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class JwtService {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String jwtIssuer;

    public String getJwt(BreweryManager manager) {
        Instant now = Instant.now();

        return Jwt.issuer(jwtIssuer)
                .upn(manager.getLogin())
                .issuedAt(now)
                .expiresAt(now.plus(4, ChronoUnit.HOURS))
                .claim("managerId", manager.getId())
                .claim("email", manager.getEmail())
                .sign();
    }
}
