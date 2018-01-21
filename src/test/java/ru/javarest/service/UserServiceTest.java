package ru.javarest.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.javarest.model.Role;
import ru.javarest.model.User;
import ru.javarest.repository.JpaUtil;
import ru.javarest.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javarest.UserTestData.*;
import static ru.javarest.UserTestData.USER;
import static ru.javarest.UserTestData.USER_ID;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }


    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "New", "new@gmail.com", "password", false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        newUser.setPassword(created.getPassword());
        assertMatch(service.getAll(),
                newUser,
                ADMIN_ENCODEPAS,
                USER_ENCODEPAS,
                USER2_ENCODEPAS,
                USER3_ENCODEPAS,
                USER4_ENCODEPAS);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new User(null, "Duplicate", "Duplicate","user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        assertMatch(service.getAll(),
                ADMIN_ENCODEPAS,
                USER2_ENCODEPAS,
                USER3_ENCODEPAS,
                USER4_ENCODEPAS);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN_ENCODEPAS);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN_ENCODEPAS);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setFirstname("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all,
                ADMIN_ENCODEPAS,
                USER_ENCODEPAS,
                USER2_ENCODEPAS,
                USER3_ENCODEPAS,
                USER4_ENCODEPAS);
    }

    @Test
    public void testEnable() {
        service.active(USER_ID, false);
        Assert.assertFalse(service.get(USER_ID).isActive());
        service.active(USER_ID, true);
        Assert.assertTrue(service.get(USER_ID).isActive());
    }



    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(null, "  ", "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        //       validateRootCause(() -> service.create(new User(null, "User", "User","mail@yandex.ru", "password", Collections.emptySet()), ConstraintViolationException.class);
        //       validateRootCause(() -> service.create(new User(null, "User", "User","mail@yandex.ru", "password", Collections.emptySet())), ConstraintViolationException.class);
    }
}