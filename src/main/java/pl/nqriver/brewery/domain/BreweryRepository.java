package pl.nqriver.brewery.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class BreweryRepository implements PanacheRepositoryBase<Brewery, UUID> {

}
