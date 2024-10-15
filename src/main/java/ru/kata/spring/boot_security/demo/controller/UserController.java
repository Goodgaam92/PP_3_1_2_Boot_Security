package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        log.info("Контроллер пользователя инициализирован");
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        log.info("Запрошена страница пользователя");
        String username = principal.getName();
        log.debug("Получение информации для пользователя: {}", username);

        User user = userService.findUserByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            log.info("Информация о пользователе {} успешно добавлена в модель", username);
        } else {
            log.warn("Пользователь {} не найден", username);
        }

        return "user";
    }
}