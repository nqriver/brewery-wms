package pl.nqriver.beer.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeerStyleRepository implements PanacheRepositoryBase<BeerStyle, Long> {
}
