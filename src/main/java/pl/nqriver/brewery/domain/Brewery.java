package pl.nqriver.brewery.domain;
import jakarta.persistence.*;
import pl.nqriver.beer.domain.Beer;
import pl.nqriver.brewery.api.BreweryResource;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "breweries")
public class Brewery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "internal_code")
    private String internalCode;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "surface_area")
    private int surfaceArea;

    @ElementCollection
    @CollectionTable(
            name="brewery_images",
            joinColumns=@JoinColumn(name="brewery_id")
    )
    @Column(name="image")
    private List<byte[]> images;

    @ManyToMany
    @JoinTable(
            name = "brewery_beer",
            joinColumns = @JoinColumn(name = "brewery_id"),
            inverseJoinColumns = @JoinColumn(name = "beer_id")
    )
    private List<Beer> producedBeers;

    public Brewery() {
    }

    public void addImage(byte[] bytes) {
        this.images.add(bytes);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(int surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public List<Beer> getProducedBeers() {
        return producedBeers;
    }

    public void setProducedBeers(List<Beer> producedBeers) {
        this.producedBeers = producedBeers;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public BreweryResource.BreweryResponse toResponse() {
        return new BreweryResource.BreweryResponse(id, name, city, postalCode, surfaceArea);
    }
}
