package ru.javarest;

import ru.javarest.matcher.BeanMatcher;
import ru.javarest.model.Role;
import ru.javarest.model.User;

import java.util.Objects;

import static ru.javarest.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "User","user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "Admin","admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final BeanMatcher<User> MATCHER = BeanMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getFirstname(), actual.getFirstname())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.isActive(), actual.isActive())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );
}
