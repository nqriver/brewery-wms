CREATE KEYSPACE IF NOT EXISTS breweries_wms
            WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

USE breweries_wms;


USE breweries_wms;

CREATE TYPE IF NOT EXISTS beer (
    id UUID,
    name TEXT,
    style TEXT
    );

CREATE TYPE IF NOT EXISTS brewery (
    id UUID,
    name TEXT,
    city TEXT,
    internal_code TEXT
    );

CREATE TABLE IF NOT EXISTS brewery_production_batch
(
    id                    UUID PRIMARY KEY,
    brewery_id            UUID,
    beer_id               UUID,
    brewery               frozen<brewery>,
    beer                  frozen<beer>,
    total_liters          BIGINT,
    production_batch_code TEXT,
    production_timestamp  TIMESTAMP,
    expiration_timestamp  TIMESTAMP
);

CREATE MATERIALIZED VIEW IF NOT EXISTS batches_by_brewery AS
SELECT *
FROM brewery_production_batch
WHERE brewery_id IS NOT NULL
  AND id IS NOT NULL
PRIMARY KEY (brewery_id, id);

CREATE MATERIALIZED VIEW IF NOT EXISTS batches_by_beer AS
SELECT *
FROM brewery_production_batch
WHERE beer_id IS NOT NULL
  AND id IS NOT NULL
PRIMARY KEY (beer_id, id);
