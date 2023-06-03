package pl.nqriver.stock.domain;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import pl.nqriver.warehouse.BeerBatchProducer.BeerBatchProducedEvent;

import java.time.Instant;
import java.util.UUID;

@Entity
@CqlName("brewery_production_batch")
public class BreweryProductionBatch {
    @PartitionKey
    @CqlName("id")
    private UUID id;
    @CqlName("beer_id")
    private UUID beerId;
    @CqlName("brewery_id")
    private UUID breweryId;
    @CqlName("beer")
    private BeerUDT beer;

    @CqlName("brewery")
    private BreweryUDT brewery;

    @CqlName("total_liters")
    private long totalLiters;

    @CqlName("production_batch_code")
    private String productionBatchCode;

    @CqlName("production_timestamp")
    private Instant productionTimestamp;

    @CqlName("expiration_timestamp")
    private Instant expirationTimestamp;


    public static BreweryProductionBatch fromEvent(BeerBatchProducedEvent event) {
        BreweryProductionBatch breweryProductionBatch = new BreweryProductionBatch();

        breweryProductionBatch.setBeerId(event.beerId());
        breweryProductionBatch.setBreweryId(event.breweryId());
        breweryProductionBatch.setProductionBatchCode(event.productionBatchCode());
        breweryProductionBatch.setProductionTimestamp(event.productionTimestamp());
        breweryProductionBatch.setBrewery(new BreweryUDT(event.breweryId(), event.breweryName(), event.breweryCity(), event.internalBreweryCode()));
        breweryProductionBatch.setBeer(new BeerUDT(event.beerId(), event.beerName(), event.beerStyle()));
        breweryProductionBatch.setTotalLiters(event.totalLiters());
        breweryProductionBatch.setId(UUID.randomUUID());
        breweryProductionBatch.setExpirationTimestamp(event.expirationTimestamp());
        return breweryProductionBatch;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBeerId() {
        return beerId;
    }

    public void setBeerId(UUID beerId) {
        this.beerId = beerId;
    }

    public UUID getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(UUID breweryId) {
        this.breweryId = breweryId;
    }

    public BeerUDT getBeer() {
        return beer;
    }

    public void setBeer(BeerUDT beer) {
        this.beer = beer;
    }

    public BreweryUDT getBrewery() {
        return brewery;
    }

    public void setBrewery(BreweryUDT brewery) {
        this.brewery = brewery;
    }

    public long getTotalLiters() {
        return totalLiters;
    }

    public void setTotalLiters(long totalLiters) {
        this.totalLiters = totalLiters;
    }

    public String getProductionBatchCode() {
        return productionBatchCode;
    }

    public void setProductionBatchCode(String productionBatchCode) {
        this.productionBatchCode = productionBatchCode;
    }

    public Instant getProductionTimestamp() {
        return productionTimestamp;
    }

    public void setProductionTimestamp(Instant productionTimestamp) {
        this.productionTimestamp = productionTimestamp;
    }

    public Instant getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Instant expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }
}
