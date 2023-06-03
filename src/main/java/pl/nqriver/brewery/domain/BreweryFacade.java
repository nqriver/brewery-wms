package pl.nqriver.brewery.domain;

import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.nqriver.beer.domain.Beer;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BreweryFacade {

    @Inject
    BreweryRepository breweryRepository;

    public Tuple2<Brewery, Beer> getRandomBeerProducedByRandomBrewery() {
        Brewery brewery = breweryRepository.findAll().stream().findAny().orElseThrow();
        Beer beer = brewery.getProducedBeers().stream().findAny().orElseThrow();
        return Tuple2.of(brewery, beer);
    }

    public Optional<Brewery> findByIdOptional(UUID breweryId) {
        return breweryRepository.findByIdOptional(breweryId);
    }
}
