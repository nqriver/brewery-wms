package pl.nqriver.brewery;

import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.nqriver.beer.domain.Beer;
import pl.nqriver.brewery.domain.Brewery;
import pl.nqriver.brewery.domain.BreweryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class BreweryFacade {

    @Inject
    BreweryRepository breweryRepository;

    public Optional<Tuple2<Brewery, Beer>> getRandomBeerProducedByRandomBrewery() {
        List<Brewery> breweries = breweryRepository.listAll();
        if (breweries.isEmpty()) {
            return Optional.empty();
        }
        Brewery randomBrewery = breweries.get(ThreadLocalRandom.current().nextInt(breweries.size()));

        List<Beer> beers = randomBrewery.getProducedBeers();
        if (beers.isEmpty()) {
            return Optional.empty();
        }
        Beer randomBeer = beers.get(ThreadLocalRandom.current().nextInt(beers.size()));

        return Optional.of(Tuple2.of(randomBrewery, randomBeer));
    }


    public Optional<Brewery> findByIdOptional(UUID breweryId) {
        return breweryRepository.findByIdOptional(breweryId);
    }

    public List<Brewery> findAll() {
        return breweryRepository.listAll();
    }
}
