package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Set;


@Controller
@Transactional
@RequestMapping(value = "/admin")
public class AdminController {
    private static final String REDIRECT_TO_ADMIN = "redirect:/admin";
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String getAdminPage(Model model) {
        model.addAttribute("allUsers", userServiceImpl.getAllUsers());
        return "admin_page";
    }

    @GetMapping(value = "/add_user")
    public String getNewUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleServiceImpl.getAllRoles());
        return "new_user_form";
    }

    @PostMapping(value = "/add_user")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") Set<Long> roleIds, Model model) {
        Set<Role> roles = roleServiceImpl.findDyIds(roleIds);

        user.setRoles(roles);
        userServiceImpl.saveUser(user);
        return REDIRECT_TO_ADMIN;
    }

    @GetMapping(value = "/{id}/show_user")
    public String showSingleUser(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userServiceImpl.findUserById(id));
        return "user";
    }

    @GetMapping(value = "/edit_user")
    public String getUserEditForm(@RequestParam("id") Long id, Model model) {
        User user = userServiceImpl.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleServiceImpl.getAllRoles());
        return "edit_user_form";
    }

    @PostMapping(value = "/{id}/edit_user")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") Set<Long> roleIds,
                             @PathVariable("id") Long id) {
        Set<Role> roles = roleServiceImpl.findDyIds(roleIds);
        user.setRoles(roles);
        user.setId(id);
        userServiceImpl.updateUser(user);
        return REDIRECT_TO_ADMIN;
    }

    @GetMapping(value = "/delete_user")
    public String deleteUserById(@RequestParam("id") Long id) {
        userServiceImpl.delete(id);
        return REDIRECT_TO_ADMIN;
    }
}
