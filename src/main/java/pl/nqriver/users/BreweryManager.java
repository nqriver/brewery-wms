package pl.nqriver.users;

import jakarta.persistence.*;
import pl.nqriver.brewery.domain.Brewery;

import java.time.Instant;
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

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @OneToOne
    @JoinColumn(name = "brewery_id", referencedColumnName = "id")
    private Brewery managedBrewery;

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

    public Brewery getManagedBrewery() {
        return managedBrewery;
    }

    public void setManagedBrewery(Brewery managedBrewery) {
        this.managedBrewery = managedBrewery;
    }
}
