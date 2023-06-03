package pl.nqriver.beer.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeerRepository implements PanacheRepositoryBase<Beer, Long> {

}
