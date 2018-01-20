package ru.javarest.repository;

import org.hibernate.jpa.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarest.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public Restaurant get(int id){return em.find(Restaurant.class, id);}

    @Transactional
    public Restaurant save(Restaurant restaurant)
    {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

    @Transactional
    public boolean delete(int id)
    {
        return em.createNamedQuery(Restaurant.DELETE).
                setParameter("id",id).
                executeUpdate() != 0;
    }

    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.ALL_SORTED, Restaurant.class).getResultList();
    }

    public Restaurant getByEmail(String email) {
        List<Restaurant> restaurants = em.createNamedQuery(Restaurant.BY_EMAIL, Restaurant.class)
                .setParameter(1, email)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return DataAccessUtils.singleResult(restaurants);
    }
}
