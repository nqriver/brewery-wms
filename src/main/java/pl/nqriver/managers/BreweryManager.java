package pl.nqriver.managers;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.*;
import pl.nqriver.brewery.domain.Brewery;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "brewery_managers")
public class BreweryManager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "login", unique = true)
    private String login;


    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "manager_breweries",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "brewery_id")
    )
    private Set<Brewery> managedBreweries = new HashSet<>();

    public BreweryManager() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Set<Brewery> getManagedBreweries() {
        return managedBreweries;
    }

    public void setManagedBreweries(Set<Brewery> managedBreweries) {
        this.managedBreweries = managedBreweries;
    }

    public void removePermissionForBrewery(Brewery brewery) {
        this.managedBreweries.remove(brewery);
    }

    static BreweryManager fromRequest(BreweryManagerResource.ManagerRegistrationRequest registrationRequest, Collection<Brewery> breweries) {
        BreweryManager manager = new BreweryManager();
        manager.setManagedBreweries(new HashSet<>(breweries));
        manager.setName(registrationRequest.name());
        manager.setLogin(registrationRequest.login());
        manager.setEmail(registrationRequest.email());
        manager.setPassword(BcryptUtil.bcryptHash(registrationRequest.password()));
        manager.setPhoneNumber(registrationRequest.phoneNumber());
        manager.setHireDate(Instant.now());
        return manager;
    }

    public BreweryManagerResource.BreweryManagerResponse toResponse() {
        return new BreweryManagerResource.BreweryManagerResponse(this.id, this.name, this.login, this.email,
                this.phoneNumber, this.hireDate);
    }

    public void removeAllPermissions() {
        this.managedBreweries.clear();
    }
}
