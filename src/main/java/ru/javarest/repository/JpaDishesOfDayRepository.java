package ru.javarest.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarest.model.Dish;
import ru.javarest.model.DishesOfDay;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaDishesOfDayRepository {

    @PersistenceContext
    private EntityManager em;

    public DishesOfDay get(int id){return em.find(DishesOfDay.class, id);}

    public List<DishesOfDay> getAll()
    {
        return em.createNamedQuery(DishesOfDay.ALL_SORTED, DishesOfDay.class).getResultList();
    }

    public List<DishesOfDay> getDishesOfDate(Date date)
    {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return em.createNamedQuery(DishesOfDay.BY_DATE).
                setParameter("datelunch", sqlDate).
                getResultList();
    }

    @Transactional
    public DishesOfDay save(DishesOfDay dishesOfDay)
    {
        if (dishesOfDay.isNew()) {
            em.persist(dishesOfDay);
            return dishesOfDay;
        } else {
            return em.merge(dishesOfDay);
        }
    }

    @Transactional
    public boolean delete(int id)
    {
        return em.createNamedQuery(DishesOfDay.DELETE).
                setParameter("id",id).
                executeUpdate() != 0;
    }
}
