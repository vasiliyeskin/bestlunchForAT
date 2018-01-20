package ru.javarest.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javarest.model.DishesOfDay;
import ru.javarest.repository.JpaDishesOfDayRepository;
import ru.javarest.util.exception.NotFoundException;

import java.util.Date;
import java.util.List;

import static ru.javarest.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishesOfDayService{

    private final JpaDishesOfDayRepository jpaDishesOfDayRepository;

    public DishesOfDayService(JpaDishesOfDayRepository jpaDishesOfDayRepository)
    {
        this.jpaDishesOfDayRepository = jpaDishesOfDayRepository;
    }

    @CacheEvict(value = "dishesofday", allEntries = true)
    public DishesOfDay create(DishesOfDay dishesOfDay) {
        Assert.notNull(dishesOfDay, "dish must not be null");
        return jpaDishesOfDayRepository.save(dishesOfDay);
    }

    @CacheEvict(value = "dishesofday", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(jpaDishesOfDayRepository.delete(id), id);
    }

    public DishesOfDay get(int id) throws NotFoundException {
        return checkNotFoundWithId(jpaDishesOfDayRepository.get(id), id);
    }

    @Cacheable("dishesofday")
    public List<DishesOfDay> getByDate(Date date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return jpaDishesOfDayRepository.getDishesOfDate(date);
    }

    @Cacheable("dishesofday")
    public List<DishesOfDay> getAll() {
        return jpaDishesOfDayRepository.getAll();
    }

    @CacheEvict(value = "dishesofday", allEntries = true)
    public void update(DishesOfDay dishesOfDay) {
        Assert.notNull(dishesOfDay, "restaurant must not be null");
        jpaDishesOfDayRepository.save(dishesOfDay);
    }

    @CacheEvict(value = "dishesofday", allEntries = true)
    public void evictCache() {
        // only for evict cache
    }
}
