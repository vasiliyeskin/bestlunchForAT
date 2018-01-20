package ru.javarest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javarest.model.DishesOfDay;
import ru.javarest.model.Restaurant;
import ru.javarest.service.DishesOfDayService;
import ru.javarest.service.RestaurantService;
import ru.javarest.util.ValidationUtil;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
public class AdminDishesOfDayRestController {

    public static final String REST_DISHESOFDAY  = "/rest/admin/dishesofday";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishesOfDayService service;

    @GetMapping(value = REST_DISHESOFDAY, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishesOfDay> getAllOfTodate()
    {
        log.info("getAll dishes of today");
        return service.getByDate(new Date());
    }

    @GetMapping(value = REST_DISHESOFDAY + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishesOfDay getDishesOfDay(@PathVariable("id") int id) {
        log.info("get dish {}", id);
        return service.get(id);
    }

    @PostMapping(value = REST_DISHESOFDAY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishesOfDay> createDishesOfDay(@RequestBody DishesOfDay dishesOfDay) {
        log.info("create dish of day {}", dishesOfDay);
        ValidationUtil.checkNew(dishesOfDay);
        DishesOfDay created = service.create(dishesOfDay);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_DISHESOFDAY + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = REST_DISHESOFDAY + "/{id}")
    public void deleteDishesOfDay(@PathVariable("id") int id) {
        log.info("delete dish of day {}", id);
        service.delete(id);
    }
}
