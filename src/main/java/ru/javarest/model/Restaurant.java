package ru.javarest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.BY_EMAIL, query = "SELECT r FROM Restaurant r WHERE r.email=?1"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY  r.title, r.email"),
})

@Access(AccessType.FIELD)
@Entity
@Table(name="restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "restaurants_unique_email_idx")})
public class Restaurant implements BaseEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String BY_EMAIL = "Restaurant.getByEmail";
    public static final String ALL_SORTED = "Restaurant.getAllSorted";

    public static final int REST_SEQ = 200000;

    @Id
    @SequenceGenerator(name = "rest_seq", sequenceName = "rest_seq", allocationSize = 1, initialValue = REST_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rest_seq")
    @Access(value = AccessType.PROPERTY)
    private Integer id;

    @NotBlank
    @Column(name = "title", nullable = false)
    protected String title;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Column(name = "site", nullable = false)
    private String site;

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    public Restaurant(){}

    public Restaurant(Restaurant restaurant) {
        this.id = restaurant.id;
        this.title = restaurant.title;
        this.address = restaurant.address;
        this.email = restaurant.email;
        this.site = restaurant.site;
        this.registered = restaurant.registered;
    }

    public Restaurant(Integer id, String title, String address, String email, String site, Date registered) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.email = email;
        this.site = site;
        this.registered = registered;
    }

    public Restaurant(Integer id, String title, String address, String email, String site) {
        this(id, title, address, email, site, new Date());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Restaurant that = (Restaurant) o;

        if (!title.equals(that.title)) return false;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", site='" + site + '\'' +
                ", registered=" + registered +
                '}';
    }
}
