package pl.nqriver.stock.domain;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;

import java.util.UUID;

@Entity
@CqlName("beer")
public record BeerUDT(UUID id, String name, String style) {
}
