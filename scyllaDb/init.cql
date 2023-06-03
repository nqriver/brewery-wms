CREATE KEYSPACE IF NOT EXISTS breweries_wms
            WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

USE breweries_wms;

CREATE TABLE IF NOT EXISTS breweryProductionBatch
(
    id                  UUID PRIMARY KEY,
    breweryId           UUID,
    beerId              UUID,
    totalLiters         BIGINT,
    productionBatchCode TEXT,
    productionTimestamp TIMESTAMP,
    expirationTimestamp TIMESTAMP
);
