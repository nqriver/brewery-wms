package pl.nqriver.stock;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import pl.nqriver.warehouse.BeerBatchProducer;
import pl.nqriver.warehouse.BeerBatchProducer.BeerBatchProducedEvent;

import java.time.Instant;
import java.util.UUID;

@Entity
@CqlName("breweryProductionBatch")
public class BreweryProductionBatch {
    @PartitionKey
    @CqlName("id")
    private UUID id;

    @CqlName("breweryId")
    private UUID breweryId;

    @CqlName("beerId")
    private UUID beerId;

    @CqlName("totalLiters")
    private long totalLiters;

    @CqlName("productionBatchCode")
    private String productionBatchCode;

    @CqlName("productionTimestamp")
    private Instant productionTimestamp;

    @CqlName("expirationTimestamp")
    private Instant expirationTimestamp;


    public static BreweryProductionBatch fromEvent(BeerBatchProducedEvent event) {
        BreweryProductionBatch breweryProductionBatch = new BreweryProductionBatch();
        breweryProductionBatch.setProductionBatchCode(event.productionBatchCode());
        breweryProductionBatch.setProductionTimestamp(event.productionTimestamp());
        breweryProductionBatch.setBreweryId(event.breweryId());
        breweryProductionBatch.setBeerId(event.beerId());
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

    public UUID getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(UUID breweryId) {
        this.breweryId = breweryId;
    }

    public UUID getBeerId() {
        return beerId;
    }

    public void setBeerId(UUID beerId) {
        this.beerId = beerId;
    }

    public Long getTotalLiters() {
        return totalLiters;
    }

    public void setTotalLiters(Long totalLiters) {
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
