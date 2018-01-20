package ru.javarest.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = DishesOfDay.DELETE, query = "DELETE FROM DishesOfDay dd WHERE dd.id=:id"),
        @NamedQuery(name = DishesOfDay.BY_DATE, query = "SELECT dd FROM DishesOfDay dd WHERE dd.datelunch=:datelunch"),
        @NamedQuery(name = DishesOfDay.ALL_SORTED, query = "SELECT dd FROM DishesOfDay dd ORDER BY dd.datelunch")
})

@Access(AccessType.FIELD)
@Entity
@Table(name = "dishesofday", uniqueConstraints = {@UniqueConstraint(columnNames = {"datelunch", "dish_id"}, name = "dishesofday_unique_datelunch_dish_id_idx")})
public class DishesOfDay implements BaseEntity {

    public static final String DELETE = "DishesOfDay.delete";
    public static final String BY_DATE = "DishesOfDay.getDishesOfDate";
    public static final String ALL_SORTED = "DishesOfDay.getAllSorted";

    @Id
    @SequenceGenerator(name = "dishofday_seq", sequenceName = "dishofday_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dishofday_seq")
    @Access(value = AccessType.PROPERTY)
    private Integer id;

    @Column(name = "datelunch", nullable = false)
    private Date datelunch;

    @Column(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Integer dish_id;

    public DishesOfDay(){}

    public DishesOfDay(Date date, Integer dish_id){
        this.datelunch = date;
        this.dish_id = dish_id;
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

    public Date getDatelunch() {
        return datelunch;
    }

    public void setDatelunch(Date datelunch) {
        this.datelunch = datelunch;
    }

    public Integer getDish() {
        return dish_id;
    }

    public void setDish(Integer dish_id) {
        this.dish_id = dish_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DishesOfDay that = (DishesOfDay) o;

        if (!datelunch.equals(that.datelunch)) return false;
        return dish_id.equals(that.dish_id);
    }

    @Override
    public int hashCode() {
        int result = datelunch.hashCode();
        result = 31 * result + dish_id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DishesOfDay{" +
                "id=" + getId() +
                ", datelunch=" + datelunch +
                ", dish_id=" + dish_id +
                '}';
    }
}
