package ru.javarest;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javarest.model.Dish;
import ru.javarest.model.Restaurant;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javarest.web.json.JsonUtil.writeIgnoreProps;

public class RestaurantDishTestData {
    public static final int rest_id = 200000;
    public static final int dish_id = 300000;

    public static final Restaurant BOLTZMANN_RESTAURANT = new Restaurant(rest_id, "boltzmann's restaurant", "Nizhny Novgorod, 5 Gagarina ave.", "boltzmann@mail.ru", "boltzmann.ru");
    public static final Restaurant MAXWELL_RESTAURANT = new Restaurant(rest_id + 1, "maxwell's restaurant", "Nizhny Novgorod, 15 Gagarina ave.", "maxwell@mail.ru", "maxwell.ru");
    public static final Restaurant FEYNMAN_RESTAURANT = new Restaurant(rest_id + 2, "feynman's restaurant", "Nizhny Novgorod, 7 Gagarina ave.", "feynman@mail.ru", "feynman.ru");


    public static final Dish LobsterMaxwell = new Dish(dish_id, "lobster", 10000, new Date(), rest_id + 1);
    public static final Dish burgerMaxwell = new Dish(dish_id+1, "burger", 2000, new Date(), rest_id + 1);
    public static final Dish borshMaxwell = new Dish(dish_id+2, "borsh", 50000, new Date(), rest_id + 1);
    public static final Dish lobsterFeynman = new Dish(dish_id+3, "lobster", 55000, new Date(), rest_id + 2);
    public static final Dish burgerFeynman = new Dish(dish_id+4, "burger", 2500, new Date(), rest_id + 2);
    public static final Dish borshBoltzmann = new Dish(dish_id+5, "borsh", 30000, new Date(), rest_id);


    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }

    public static <T> void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
    }

    public static <T> ResultMatcher contentJson(T... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeIgnoreProps(expected, "registered"));
    }
}
