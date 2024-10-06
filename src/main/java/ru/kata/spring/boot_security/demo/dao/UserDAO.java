package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserDAO {
    void addUser(User user);

    List<User> getAllUsers();

    void removeUserById(Long id);

    User getUserById(Long id);

    void updateUser(User user);
    User findByUsername(String username);
}
