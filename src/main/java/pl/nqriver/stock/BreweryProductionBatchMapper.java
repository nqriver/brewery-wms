package pl.nqriver.stock;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface BreweryProductionBatchMapper {
    @DaoFactory
    BreweryProductionBatchDao breweryDao();
}

