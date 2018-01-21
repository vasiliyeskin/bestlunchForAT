package ru.javarest;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javarest.model.Restaurant;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javarest.web.json.JsonUtil.writeIgnoreProps;

public class RestaurantTestData {
    public static final int rest_id = 200000;

    public static final Restaurant BoltzmannRestaurant = new Restaurant(rest_id, "boltzmann's restaurant", "Nizhny Novgorod, 5 Gagarina ave.", "boltzmann@mail.ru", "boltzmann.ru");
    public static final Restaurant MaxwellRestaurant = new Restaurant(rest_id + 1, "maxwell's restaurant", "Nizhny Novgorod, 15 Gagarina ave.", "maxwell@mail.ru", "maxwell.ru");
    public static final Restaurant FeynmanRestaurant = new Restaurant(rest_id + 2, "feynman's restaurant", "Nizhny Novgorod, 7 Gagarina ave.", "feynman@mail.ru", "feynman.ru");



    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return content().json(writeIgnoreProps(expected, "registered"));
    }
}
