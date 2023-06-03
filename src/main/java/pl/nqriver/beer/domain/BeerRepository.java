package pl.nqriver.beer.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class BeerRepository implements PanacheRepositoryBase<Beer, UUID> {

}
