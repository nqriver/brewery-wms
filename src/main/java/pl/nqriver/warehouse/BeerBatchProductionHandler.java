package pl.nqriver.warehouse;

import io.quarkus.logging.Log;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.nqriver.stock.domain.BreweryProductionBatch;
import pl.nqriver.stock.domain.BreweryProductionBatchDao;

@ApplicationScoped
public class BeerBatchProductionHandler {

    @Inject
    BreweryProductionBatchDao productionBatchDao;


    @ConsumeEvent(BeerBatchProducer.BEER_BATCH_PRODUCED_TOPIC)
    @Blocking
    public void consumeEvent(BeerBatchProducedEvent event) {
        BreweryProductionBatch breweryProductionBatch = BreweryProductionBatch.fromEvent(event);
        productionBatchDao.save(breweryProductionBatch);
        Log.infof("New brewery batch registered {%s}", event);
    }

}
