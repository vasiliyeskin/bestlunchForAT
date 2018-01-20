package ru.javarest.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javarest.model.Role;
import ru.javarest.model.User;
import ru.javarest.repository.JpaUtil;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;

abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(null, "  ", "  ","mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "User","  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "User","mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
 //       validateRootCause(() -> service.create(new User(null, "User", "User","mail@yandex.ru", "password", Collections.emptySet()), ConstraintViolationException.class);
 //       validateRootCause(() -> service.create(new User(null, "User", "User","mail@yandex.ru", "password", Collections.emptySet())), ConstraintViolationException.class);
    }
}