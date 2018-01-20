package ru.javarest.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javarest.UserTestData;
import ru.javarest.model.Role;
import ru.javarest.model.User;
import ru.javarest.web.AbstractControllerTest;
import ru.javarest.web.AdminUsersRestController;
import ru.javarest.web.json.JsonUtil;
import ru.javarest.TestUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminUsersRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminUsersRestController.REST_USERS + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER.contentMatcher(UserTestData.ADMIN));
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + UserTestData.USER.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER.contentMatcher(UserTestData.USER));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + UserTestData.USER_ID))
                .andDo(print())
                .andExpect(status().isOk());
        UserTestData.MATCHER.assertListEquals(Collections.singletonList(UserTestData.ADMIN), userService.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(UserTestData.USER);
        updated.setFirstname("UpdatedName");
        updated.setLastname("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        UserTestData.MATCHER.assertEquals(updated, userService.get(UserTestData.USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "New","new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        User returned = UserTestData.MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        UserTestData.MATCHER.assertEquals(expected, returned);
        UserTestData.MATCHER.assertListEquals(Arrays.asList(UserTestData.ADMIN, expected, UserTestData.USER), userService.getAll());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER.contentListMatcher(UserTestData.ADMIN, UserTestData.USER)));
    }
}