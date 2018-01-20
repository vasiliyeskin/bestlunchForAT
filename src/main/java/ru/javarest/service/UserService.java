package ru.javarest.service;


import ru.javarest.model.User;
import ru.javarest.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void evictCache();

    List<User> getAll();

    User getWithMeals(int id);
}