package pl.nqriver.stock;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

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
}

