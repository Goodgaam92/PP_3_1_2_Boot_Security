package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String REDIRECT_TO_ADMIN = "redirect:/admin";
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        log.info("Контроллер администратора инициализирован");
    }

    @GetMapping()
    public String getAdminPage(Model model) {
        log.info("Запрошена страница администратора");
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin_page";
    }

    @GetMapping("/users/add")
    public String getNewUserForm(@ModelAttribute("user") User user, Model model) {
        log.info("Запрошена форма добавления нового пользователя");
        model.addAttribute("roles", roleService.getAllRoles());
        return "new_user_form";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roles", required = false) Set<Long> roleIds) {
        log.info("Добавление нового пользователя: {}", user.getUsername());
        if (roleIds != null) {
            Set<Role> roles = roleService.findDyIds(roleIds);
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return REDIRECT_TO_ADMIN;
    }

    @GetMapping("/users/{id}")
    public String showSingleUser(@PathVariable("id") Long id, Model model) {
        log.info("Запрошена информация о пользователе с id: {}", id);
        model.addAttribute("user", userService.findUserById(id));
        return "user";
    }

    @GetMapping("/users/{id}/edit")
    public String getUserEditForm(@PathVariable("id") Long id, Model model) {
        log.info("Запрошена форма редактирования для пользователя с id: {}", id);
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit_user_form";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) Set<Long> roleIds,
                             @PathVariable("id") Long id) {
        log.info("Обновление информации пользователя с id: {}", id);
        if (roleIds != null) {
            Set<Role> roles = roleService.findDyIds(roleIds);
            user.setRoles(roles);
        }
        user.setId(id);
        userService.updateUser(user);
        return REDIRECT_TO_ADMIN;
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable("id") Long id) {
        log.info("Удаление пользователя с id: {}", id);
        userService.delete(id);
        return REDIRECT_TO_ADMIN;
    }
}
