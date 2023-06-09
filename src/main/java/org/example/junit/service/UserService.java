package org.example.junit.service;

import org.example.junit.dao.UserDao;
import org.example.junit.model.User;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserService {
    private final UserDao userDao;

    private final List<User> users = new ArrayList<>();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> getAll() {
        return users;
    }

    public void add(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public boolean delete(Integer userId) {
        return userDao.delete(userId);
    }

    public Optional<User> login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("username or password is null");
        }
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();

    }

    public Map<Integer, User> getAllConvertedById() {
        return users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
