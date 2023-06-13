package pl.nqriver.brewery.domain;

import jakarta.persistence.*;
import pl.nqriver.beer.domain.Beer;
import pl.nqriver.brewery.api.BreweryResource;

import java.util.List;
import java.util.Objects;
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

    @Column(name = "internal_code", unique = true)
    private String internalCode;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "surface_area")
    private int surfaceArea;

    @ElementCollection
    @CollectionTable(
            name = "brewery_images",
            joinColumns = @JoinColumn(name = "brewery_id")
    )
    @Column(name = "image")
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

    static Brewery fromRequest(BreweryResource.BreweryCreateRequest breweryCreateRequest, List<Beer> producedBeers) {
        Brewery brewery = new Brewery();
        brewery.setName(breweryCreateRequest.name());
        brewery.setCity(breweryCreateRequest.city());
        brewery.setPostalCode(breweryCreateRequest.postalCode());
        brewery.setSurfaceArea(breweryCreateRequest.surfaceArea());
        brewery.setInternalCode(breweryCreateRequest.internalCode());
        brewery.setProducedBeers(producedBeers);
        return brewery;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brewery brewery = (Brewery) o;
        return Objects.equals(internalCode, brewery.internalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode);
    }

    public void addBeer(Beer beer) {
        this.producedBeers.add(beer);
    }

    public boolean producesBeer(Beer beer) {
        return producedBeers.contains(beer);
    }

    public void closeProduction(Beer beer) {
        producedBeers.remove(beer);
    }
}
