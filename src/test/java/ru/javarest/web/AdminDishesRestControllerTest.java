package ru.javarest.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.javarest.TestUtil;
import ru.javarest.model.Dish;
import ru.javarest.service.DishService;
import org.springframework.http.MediaType;
import ru.javarest.web.json.JsonUtil;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javarest.RestaurantDishTestData.*;
import static ru.javarest.TestUtil.userHttpBasic;
import static ru.javarest.UserTestData.ADMIN;

public class AdminDishesRestControllerTest extends AbstractControllerTest  {

    private static final String REST_URL = AdminDishesRestController.REST_DISHES;

    @Autowired
    protected DishService dishService;

//    get All Dishes
//    http://localhost:8080/bestlunch/rest/admin/dishes --user admin@gmail.com:admin
    @Test
    public void getAllDishes() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        LOBSTER_MAXWELL,
                        BURGER_MAXWELL,
                        BORSH_MAXWELL,
                        LOBSTER_FEYNMAN,
                        BURGER_FEYNMAN,
                        BORSH_BOLTZMANN));
    }

//  get Dish 300000
//  http://localhost:8080/bestlunch/rest/admin/dishes/300000 --user admin@gmail.com:admin`
    @Test
    public void getDish() throws Exception {
        mockMvc.perform(get(REST_URL + "/300000")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(LOBSTER_MAXWELL));
    }

//  create Dish
//  http://localhost:8080/bestlunch/rest/admin/dishes --user admin@gmail.com:admin`
    @Test
    public void createDish() throws Exception {
        Dish expected = new Dish(null, "steak", 20000, new Date(), BOLTZMANN_RESTAURANT.getId());
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(dishService.getAll(),
                LOBSTER_MAXWELL,
                BURGER_MAXWELL,
                BORSH_MAXWELL,
                LOBSTER_FEYNMAN,
                BURGER_FEYNMAN,
                BORSH_BOLTZMANN,
                expected);
    }

//  delete Dish
//  http://localhost:8080/bestlunch/rest/admin/dishes/300001 --user admin@gmail.com:admin
    @Test
    public void deleteDish() throws Exception {
            mockMvc.perform(delete(REST_URL + "/300001")
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isOk());
            assertMatch(dishService.getAll(),
                    LOBSTER_MAXWELL,
                    BORSH_MAXWELL,
                    LOBSTER_FEYNMAN,
                    BURGER_FEYNMAN,
                    BORSH_BOLTZMANN);
    }

    @Test
    public void updateDish() throws Exception {
        Dish updated = new Dish(LOBSTER_MAXWELL);
        updated.setPrice(55566);
        mockMvc.perform(put(REST_URL + "/" + LOBSTER_MAXWELL.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(dishService.get(LOBSTER_MAXWELL.getId()), updated);

    }
}