package pl.nqriver.warehouse;

import java.time.Instant;
import java.util.UUID;

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
