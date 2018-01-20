package ru.javarest.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = Dish.ALL_RESTAUR_SORTED, query = "SELECT d FROM Dish d WHERE d.restaurant_id=:restaurant_id ORDER BY d.name DESC"),
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dish d ORDER BY d.name DESC"),
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish d WHERE d.id=:id")})


@Access(AccessType.FIELD)
@Entity
@Table(name="dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "dishes_unique_restaurant_name_idx")})
public class Dish implements BaseEntity {
    public static final String ALL_RESTAUR_SORTED = "Dish.getAllRestaurant";
    public static final String ALL_SORTED = "Dish.getAll";
    public static final String DELETE = "Dish.delete";


    public static final int DISH_SEQ = 300000;

    @Id
    @SequenceGenerator(name = "dish_seq", sequenceName = "dish_seq", allocationSize = 1, initialValue = DISH_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_seq")
    @Access(value = AccessType.PROPERTY)
    private Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @Column(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Integer restaurant_id;

    public Dish(){}

    public Dish(Dish dish)
    {
        this.id = dish.id;
        this.name = dish.name;
        this.price = dish.price;
        this.registered = dish.registered;
        this.restaurant_id = dish.restaurant_id;
    }

    public Dish(Integer id, String name, Integer price, Date registered, Integer restaurant_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.registered = registered;
        this.restaurant_id = restaurant_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Integer getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurantId(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dish dish = (Dish) o;

        if (!name.equals(dish.name)) return false;
        return price.equals(dish.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", registered=" + registered +
                ", restaurant_id=" + restaurant_id +
                '}';
    }
}
