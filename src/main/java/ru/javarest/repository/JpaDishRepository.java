package ru.javarest.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarest.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaDishRepository {

    @PersistenceContext
    private EntityManager em;

    public Dish get(int id){return em.find(Dish.class, id);}

    @Transactional
    public Dish save(Dish dish)
    {
        if (dish.isNew()) {
            em.persist(dish);
            return dish;
        } else {
            return em.merge(dish);
        }
    }

    @Transactional
    public boolean delete(int id)
    {
        return em.createNamedQuery(Dish.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

    public List<Dish> getAll()
    {
        return em.createNamedQuery(Dish.ALL_SORTED, Dish.class).getResultList();
    }

    public List<Dish> getAllRestaurant(int restaurant_id)
    {
        return em.createNamedQuery(Dish.ALL_RESTAUR_SORTED, Dish.class).
                setParameter("restaurant_id", restaurant_id).
                getResultList();
    }
}
