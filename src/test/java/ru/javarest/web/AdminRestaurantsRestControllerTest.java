package ru.javarest.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javarest.TestUtil;
import ru.javarest.model.Restaurant;
import ru.javarest.service.RestaurantService;
import ru.javarest.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javarest.RestaurantTestData.*;
import static ru.javarest.TestUtil.userHttpBasic;
import static ru.javarest.UserTestData.ADMIN;

public class AdminRestaurantsRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/admin/restaurants";


    @Autowired
    protected RestaurantService restaurantService;

    //  get All Restaurants
//  http://localhost:8080/bestlunch/rest/admin/restaurants --user admin@gmail.com:admin`
    @Test
    public void getAllRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        BoltzmannRestaurant,
                        MaxwellRestaurant,
                        FeynmanRestaurant));
    }

    // get Restaurant with id=200001
    //http://localhost:8080/bestlunch/rest/admin/restaurants/200001 --user admin@gmail.com:admin`
    @Test
    public void getRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/200000")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(BoltzmannRestaurant));
    }

    @Test
    public void createWithLocationRestaurant() throws Exception {
        Restaurant expected = new Restaurant(null, "gibbs's restaurant", "Nizhny Novgorod, 75 Gagarina ave.", "gibbs@mail.ru", "gibbs.ru");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(restaurantService.getAll(), BoltzmannRestaurant,
                FeynmanRestaurant,
                expected,
                MaxwellRestaurant);
    }

//  delete Restaurant
//  DELETE http://localhost:8080/bestlunch/rest/admin/restaurants/200001 --user admin@gmail.com:admin
    @Test
    public void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + "/200001")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());
        assertMatch(restaurantService.getAll(), BoltzmannRestaurant, FeynmanRestaurant);
    }

//    #### update Restaurant
//`curl -s -X PUT -d '{"title":"gell","address":"Nizhny Novgorod, 13 Gagarina ave.","email":"gell-mann@mail.ru","site":"gell.ru"}' -H 'Content-Type: application/json' http://localhost:8080/bestlunch/rest/admin/restaurants/200003 --user admin@gmail.com:admin
    @Test
    public void updateRestaurant() throws Exception {
        Restaurant updated = new Restaurant(BoltzmannRestaurant);
        updated.setAddress("New Address");
        mockMvc.perform(put(REST_URL + "/" + BoltzmannRestaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(restaurantService.get(BoltzmannRestaurant.getId()), updated);

    }
}