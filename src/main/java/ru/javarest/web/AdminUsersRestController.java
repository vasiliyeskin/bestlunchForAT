package ru.javarest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javarest.model.Dish;
import ru.javarest.model.Restaurant;
import ru.javarest.model.User;
import ru.javarest.service.DishService;
import ru.javarest.service.RestaurantService;
import ru.javarest.service.UserService;
import ru.javarest.util.ValidationUtil;

import java.net.URI;
import java.util.List;

@RestController
public class AdminUsersRestController {
    public static final String REST_USERS  = "/rest/admin/users";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @GetMapping(value = REST_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        log.info("getAll");
        return userService.getAll();
    }

    @GetMapping(value = REST_USERS + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") int id) {
        log.info("get user {}", id);
        return userService.get(id);
    }

    @PostMapping(value = REST_USERS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocationUser(@RequestBody User user) {
        log.info("create user {}", user);
        ValidationUtil.checkNew(user);
        User created = userService.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_USERS + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = REST_USERS + "/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        log.info("delete user {}", id);
        userService.delete(id);
    }

    @PutMapping(value = REST_USERS + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody User user, @PathVariable("id") int id) {
        log.info("update user {} with id={}", user, id);
        ValidationUtil.assureIdConsistent(user, id);
        userService.update(user);
    }

    @GetMapping(value = REST_USERS + "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByMail(@RequestParam("email") String email) {
        log.info("getByEmail {}", email);
        return userService.getByEmail(email);
    }
}