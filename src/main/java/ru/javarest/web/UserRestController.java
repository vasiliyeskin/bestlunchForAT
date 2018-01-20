package ru.javarest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javarest.AuthorizedUser;
import ru.javarest.model.Dish;
import ru.javarest.model.DishesOfDay;
import ru.javarest.model.Restaurant;
import ru.javarest.model.Vote;
import ru.javarest.service.DishService;
import ru.javarest.service.DishesOfDayService;
import ru.javarest.service.RestaurantService;
import ru.javarest.service.VoteService;

import java.net.URI;
import java.util.*;

@RestController
public class UserRestController {
    public static final String REST_VOTES = "/rest/vote";

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private VoteService voteService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DishService dishService;
    @Autowired
    private DishesOfDayService dishesOfDayService;


    @GetMapping(value = REST_VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAlltoday() {
        log.info("getAllbyDate");
        return voteService.getByDate(new Date());
    }

    @GetMapping(value = REST_VOTES + "/currentuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getCurrentUserTodayVote() {
        log.info("get Current User Today Vote");
        return voteService.get(AuthorizedUser.id(), new Date());
    }

    @PostMapping(value = REST_VOTES + "/{restaurant_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@PathVariable("restaurant_id") int restaurant_id) {

        Vote vote = voteService.get(AuthorizedUser.id(), new Date());
        if (vote == null) {
            vote = new Vote(new Date(), AuthorizedUser.id(), restaurant_id);
        } else {
            vote.setRestaurant_id(restaurant_id);
        }

        log.info("create vote {}", vote);
        Vote created = voteService.create(vote);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_VOTES + "/today")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @DeleteMapping(value = REST_VOTES + "/{id}")
    public void deleteVote(@PathVariable("id") int id) {
        log.info("delete vote {}", id);
        voteService.delete(id);
    }

    @GetMapping(value = REST_VOTES + "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> getResultToday() {
        log.info("get Result today");
        List<Vote> votes = voteService.getByDate(new Date());
        List<Restaurant> restaurants = restaurantService.getAll();
        List<Integer> restr_ids = new ArrayList<>();

        for (Restaurant r : restaurants) {
            restr_ids.add(r.getId());
        }

        Map<Integer, Integer> mapRest = new HashMap<>();
        Map<String, Integer> mapRestName = new HashMap<>();
        for (Vote v : votes) {
            if (mapRest.containsKey(v.getRestaurant_id())) {
                mapRest.put(v.getRestaurant_id(), 1 + mapRest.get(v.getRestaurant_id()));
                mapRestName.put(restaurants.get(restr_ids.indexOf((Integer) v.getRestaurant_id())).getTitle(), mapRest.get(v.getRestaurant_id()));
            } else {
                mapRest.put(v.getRestaurant_id(), 1);
                mapRestName.put(restaurants.get(restr_ids.indexOf((Integer) v.getRestaurant_id())).getTitle(), 1);
            }
        }

        return mapRestName;
    }


    @GetMapping(value = "/rest/dishesofday", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishesOfDay> getDishesOfDay() {
        log.info("getAll dishes of today");
        return dishesOfDayService.getByDate(new Date());
    }

    @GetMapping(value = "/rest/rad", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, RestaurantsAndDishesOfDay> getRestaurantAndDishes() {
        log.info("get All today restaurant and dishes");

        Map<Integer, RestaurantsAndDishesOfDay> mapRaD = new HashMap<>();
        Dish dish = new Dish();
        int idDish = 0, idRest = 0;
        for (DishesOfDay dd : dishesOfDayService.getByDate(new Date())) {
            idDish = dd.getDish();
            dish = dishService.get(idDish);
            idRest = dish.getRestaurantId();
            if (mapRaD.containsKey(idRest)) {
                mapRaD.get(idRest).dishes.add(dish);
            } else {
                mapRaD.put(idRest, new RestaurantsAndDishesOfDay(restaurantService.get(idRest)));
                mapRaD.get(idRest).dishes.add(dish);
            }
        }

        return mapRaD;
    }

    public class RestaurantsAndDishesOfDay {
        protected String title;
        private String address;
        private String email;
        private String site;
        public List<Dish> dishes;

        public RestaurantsAndDishesOfDay(Restaurant r) {
            this.address = r.getAddress();
            this.email = r.getEmail();
            this.site = r.getSite();
            this.title = r.getTitle();

            dishes = new ArrayList<>();
        }
    }


}
