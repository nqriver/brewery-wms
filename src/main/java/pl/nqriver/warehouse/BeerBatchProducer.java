package pl.nqriver.warehouse;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.bytebuddy.utility.RandomString;
import pl.nqriver.beer.domain.Beer;
import pl.nqriver.brewery.domain.Brewery;
import pl.nqriver.brewery.domain.BreweryFacade;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class BeerBatchProducer {

    public static final String BEER_BATCH_PRODUCED_TOPIC = "beer-batch-produced";
    public static final AtomicInteger BATCH_COUNTER = new AtomicInteger(0);

    public record BeerBatchProducedEvent(
            UUID breweryId,
            String breweryName,
            String internalBreweryCode,
            String breweryCity,
            UUID beerId,
            String beerStyle,
            String beerName,
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

        breweryFacade.getRandomBeerProducedByRandomBrewery()
                .ifPresentOrElse(tuple -> {
                            Beer beer = tuple.getItem2();
                            Brewery brewery = tuple.getItem1();

                            int quantity = ThreadLocalRandom.current().nextInt(1, 5000);
                            int liters = (int) ((double) quantity * beer.getBottleCapacity());

                            BeerBatchProducedEvent event = new BeerBatchProducedEvent(
                                    brewery.getId(),
                                    brewery.getName(),
                                    brewery.getInternalCode(),
                                    brewery.getCity(),
                                    beer.getId(),
                                    beer.getStyle().getName(),
                                    beer.getName(),
                                    quantity,
                                    liters,
                                    RandomString.make(5) + BATCH_COUNTER.getAndIncrement(),
                                    Instant.now(),
                                    Instant.now().plusSeconds(Duration.ofDays(365).toSeconds()));

                            eventBus.publish(BEER_BATCH_PRODUCED_TOPIC, event);
                            Log.infof("Event emitted {%s}", event);

                        },
                        () -> Log.warn("Cannot find beer production goal set to any brewery. No brewery produces no beer"));

    }


}
