package pl.nqriver.beer.domain;

import jakarta.persistence.*;
import pl.nqriver.beer.api.BeerStyleResource;

@Entity
@Table(name = "beer_styles")
public class BeerStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public BeerStyle() {
    }

    public BeerStyle(String name) {
        this.name = name;
    }

    public static BeerStyle fromDto(BeerStyleResource.BeerStyleDto dto) {
        return new BeerStyle(dto.name());
    }

    public BeerStyleResource.BeerStyleDto toDto() {
        return new BeerStyleResource.BeerStyleDto(this.id, this.name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
