package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> getAllUsers();

    void delete(Long userId);

    User findUserById(Long userId);

    void updateUser(User user);

    User findUserByUsername(String username);
}
