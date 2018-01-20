package ru.javarest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javarest.model.Restaurant;
import ru.javarest.service.RestaurantService;
import ru.javarest.util.ValidationUtil;

import java.net.URI;
import java.util.List;

@RestController
public class AdminRestaurantsRestController {

    public static final String REST_RESTS  = "/rest/admin/restaurants";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(value = REST_RESTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants()
    {
        log.info("getAll restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = REST_RESTS + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    @PostMapping(value = REST_RESTS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocationRestaurant(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_RESTS + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = REST_RESTS + "/{id}")
    public void deleteRestaurant(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    @PutMapping(value = REST_RESTS + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("update restaurant {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @GetMapping(value = REST_RESTS + "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurantByMail(@RequestParam("email") String email) {
        log.info("getByEmail {}", email);
        return restaurantService.getByEmail(email);
    }

}
