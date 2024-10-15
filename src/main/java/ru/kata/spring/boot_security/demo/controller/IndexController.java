package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    public IndexController() {
        log.info("Контроллер индексной страницы инициализирован");
    }

    @GetMapping("/")
    public String indexPage() {
        log.info("Запрошена индексная страница");
        return "index";
    }
}