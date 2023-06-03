package pl.nqriver.warehouse;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.nqriver.beer.domain.Beer;
import pl.nqriver.brewery.domain.Brewery;
import pl.nqriver.brewery.domain.BreweryFacade;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class BeerBatchProducer {

    public static final String BEER_BATCH_PRODUCED_TOPIC = "beer-batch-produced";

    public record BeerBatchProducedEvent(
            UUID breweryId,
            UUID beerId,
            int quantity,
            long totalLiters,
            String productionBatchCode,
            Instant productionTimestamp,
            Instant expirationTimestamp
    ) {
    }


    @Inject
    EventBus eventBus;


    @Inject
    BreweryFacade breweryFacade;


    @Scheduled(every = "10s")
    void produceBeers() {
        emitBeerBatchProduced();
    }

    public void emitBeerBatchProduced() {

        Tuple2<Brewery, Beer> randomBeerProducedByRandomBrewery = breweryFacade.getRandomBeerProducedByRandomBrewery();

        Beer beer = randomBeerProducedByRandomBrewery.getItem2();
        Brewery brewery = randomBeerProducedByRandomBrewery.getItem1();

        BeerBatchProducedEvent event = new BeerBatchProducedEvent(
                brewery.getId(),
                beer.getId(),
                23,
                2424,
                "asd",
                Instant.now(),
                Instant.now().plusSeconds(Duration.ofDays(365).toSeconds()));

        Log.infov("Event emitted {}", event);

        eventBus.publish(BEER_BATCH_PRODUCED_TOPIC, event);
    }


}
