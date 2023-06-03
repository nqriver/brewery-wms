package pl.nqriver.stock.domain;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;

import java.util.UUID;

@Dao
public interface BreweryProductionBatchDao {
    @Select
    BreweryProductionBatch findById(UUID id);

    @Insert
    void save(BreweryProductionBatch breweryProductionBatch);

    @Update
    void update(BreweryProductionBatch breweryProductionBatch);



    @Delete
    void delete(BreweryProductionBatch breweryProductionBatch);

    @Select
    PagingIterable<BreweryProductionBatch> findAll();

    @Query("SELECT * FROM batches_by_brewery WHERE brewery_id = :breweryId")
    PagingIterable<BreweryProductionBatch> findByBreweryId(@CqlName("breweryId") UUID breweryId);

    @Query("SELECT * FROM batches_by_beer WHERE beer_id = :beerId")
    PagingIterable<BreweryProductionBatch> findByBeerId(@CqlName("beerId") UUID beerId);

}

