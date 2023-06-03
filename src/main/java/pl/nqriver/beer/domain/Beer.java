package pl.nqriver.beer.domain;

import jakarta.persistence.*;
import pl.nqriver.beer.api.BeerResource;

import java.util.UUID;

@Entity
@Table(name = "beers")
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "beer_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "style_id", referencedColumnName = "id")
    private BeerStyle style;

    @Column(name = "ibu")
    private double ibu;

    @Column(name = "bottle_capacity")
    private double bottleCapacity;

    @Column(name = "alcohol_percentage")
    private double alcoholPercentage;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    public static Beer fromRequest(BeerResource.CreateBeerRequest createBeerRequest, BeerStyle style) {
        Beer beer = new Beer();
        beer.setColor(createBeerRequest.color());
        beer.setIbu(createBeerRequest.ibu());
        beer.setBottleCapacity(createBeerRequest.bottleCapacity());
        beer.setAlcoholPercentage(createBeerRequest.alcoholPercentage());
        beer.setDescription(createBeerRequest.description());
        beer.setName(createBeerRequest.name());
        beer.setPrice(createBeerRequest.price());
        beer.setStyle(style);
        return beer;
    }

    public BeerResource.BeerResponse toResponse() {
        return new BeerResource.BeerResponse(
                getId(),
                getName(),
                getStyle().getName(),
                getIbu(),
                getBottleCapacity(),
                getAlcoholPercentage(),
                getColor(),
                getDescription(),
                getPrice()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeerStyle getStyle() {
        return style;
    }

    public void setStyle(BeerStyle style) {
        this.style = style;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public double getBottleCapacity() {
        return bottleCapacity;
    }

    public void setBottleCapacity(double bottleCapacity) {
        this.bottleCapacity = bottleCapacity;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

