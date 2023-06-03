package pl.nqriver.brewery.domain;

import jakarta.persistence.*;
import pl.nqriver.beer.domain.Beer;

@Entity
@Table(name = "brewery_stock")
public class BreweryStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brewery_id")
    private Brewery brewery;

    @ManyToOne
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @Column(name = "quantity")
    private int quantity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
