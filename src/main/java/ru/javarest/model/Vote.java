package ru.javarest.model;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id and v.user_id=:user_id"),
        @NamedQuery(name = Vote.BY_DATE, query = "SELECT v FROM Vote v WHERE v.datelunch=:datelunch"),
        @NamedQuery(name = Vote.VOTE_BY_DATE, query = "SELECT v FROM Vote v WHERE v.datelunch=:datelunch and v.user_id=:user_id")
})

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String BY_DATE = "Vote.getAllByDate";
    public static final String VOTE_BY_DATE = "Vote.getVoteByDate";

    @Column(name = "datelunch", nullable = false)
    private Date datelunch;

    @Column(name = "user_id", nullable = false)
    private Integer user_id;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurant_id;

    public Vote() {}

    public Vote(Integer id, Date datelunch, Integer user_id, Integer restaurant_id) {
        super(id);
        this.datelunch = datelunch;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
    }

    public Vote(Date datelunch, Integer user_id, Integer restaurant_id) {
        this.datelunch = datelunch;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
    }

    public Date getDatelunch() {
        return datelunch;
    }

    public void setDatelunch(Date datelunch) {
        this.datelunch = datelunch;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        if (!super.equals(o)) return false;

        Vote vote = (Vote) o;

        if (!getUser_id().equals(vote.getUser_id())) return false;
        return getRestaurant_id().equals(vote.getRestaurant_id());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getUser_id().hashCode();
        result = 31 * result + getRestaurant_id().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + getId() +
                ", datelunch=" + datelunch +
                ", user_id=" + user_id +
                ", restaurant_id=" + restaurant_id +
                '}';
    }
}
