package pl.nqriver.warehouse;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.nqriver.stock.BreweryProductionBatch;
import pl.nqriver.stock.BreweryProductionBatchDao;
import pl.nqriver.warehouse.BeerBatchProducer.BeerBatchProducedEvent;

import java.util.UUID;

@ApplicationScoped
public class BeerBatchProductionHandler {

    @Inject
    BreweryProductionBatchDao productionBatchDao;


    @ConsumeEvent(BeerBatchProducer.BEER_BATCH_PRODUCED_TOPIC)
    public void consumeEvent(BeerBatchProducedEvent event) {
        BreweryProductionBatch breweryProductionBatch = new BreweryProductionBatch();
        breweryProductionBatch.setProductionBatchCode(event.productionBatchCode());
        breweryProductionBatch.setProductionTimestamp(event.productionTimestamp());
        breweryProductionBatch.setBreweryId(event.breweryId());
        breweryProductionBatch.setBeerId(event.beerId());
        breweryProductionBatch.setTotalLiters(event.totalLiters());
        breweryProductionBatch.setId(UUID.randomUUID());
        breweryProductionBatch.setExpirationTimestamp(event.expirationTimestamp());
        productionBatchDao.save(breweryProductionBatch);
    }
}
