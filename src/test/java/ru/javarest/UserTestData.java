package ru.javarest;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javarest.model.Role;
import ru.javarest.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javarest.model.AbstractBaseEntity.START_SEQ;
import static ru.javarest.web.json.JsonUtil.writeIgnoreProps;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "Smith","user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "Smith","admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER2 = new User(USER_ID+2, "User2", "Smith", "user2@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER3 = new User(USER_ID+3, "User3", "Smith", "user3@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER4 = new User(USER_ID+4, "User4", "Smith", "user4@yandex.ru", "password", Role.ROLE_USER);

    public static final User USER_ENCODEPAS = new User(USER_ID, "User", "Smith","user@yandex.ru", "$2a$10$i09JKq1iXzAYl2o6yLvfBuJYRXhypm9P06voTexk0X6N46qTNt606", Role.ROLE_USER);
    public static final User ADMIN_ENCODEPAS = new User(ADMIN_ID, "Admin", "Smith","admin@gmail.com", "$2a$10$M7C82phiJOuaQSS/ygZVtebkxmsUWEN9ZdTVstJsQcEaRqf2bAz0C", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER2_ENCODEPAS = new User(USER_ID+2, "User2", "Smith", "user2@yandex.ru", "$2a$10$i09JKq1iXzAYl2o6yLvfBuJYRXhypm9P06voTexk0X6N46qTNt606", Role.ROLE_USER);
    public static final User USER3_ENCODEPAS = new User(USER_ID+3, "User3", "Smith", "user3@yandex.ru", "$2a$10$i09JKq1iXzAYl2o6yLvfBuJYRXhypm9P06voTexk0X6N46qTNt606", Role.ROLE_USER);
    public static final User USER4_ENCODEPAS = new User(USER_ID+4, "User4", "Smith", "user4@yandex.ru", "$2a$10$i09JKq1iXzAYl2o6yLvfBuJYRXhypm9P06voTexk0X6N46qTNt606", Role.ROLE_USER);


    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered"));
    }
}
