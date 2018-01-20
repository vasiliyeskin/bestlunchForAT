package ru.javarest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javarest.model.Dish;
import ru.javarest.service.DishService;
import ru.javarest.util.ValidationUtil;

import java.net.URI;
import java.util.List;

@RestController
public class AdminDishesRestController {

    public static final String REST_DISHES = "/rest/admin/dishes";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;

    @GetMapping(value = REST_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllDishes()
    {
        log.info("getAll dishes");
        return dishService.getAll();
    }

     @GetMapping(value = REST_DISHES + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDish(@PathVariable("id") int id) {
        log.info("get dish {}", id);
        return dishService.get(id);
    }

    @PostMapping(value = REST_DISHES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        log.info("create dish {}", dish);
        ValidationUtil.checkNew(dish);
        Dish created = dishService.create(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_DISHES + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = REST_DISHES + "/{id}")
    public void deleteDish(@PathVariable("id") int id) {
        log.info("delete dish {}", id);
        dishService.delete(id);
    }

    @PutMapping(value = REST_DISHES + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateDish(@RequestBody Dish dish, @PathVariable("id") int id) {
        log.info("update dish {} with id={}", dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        dishService.update(dish);
    }
}
