package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import java.util.List;

@ThreadSafe
@Controller
public class UserController {
    private final UserService users;


    public UserController(UserService users) {
        this.users = users;
    }
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", users.getAllUsers());
        return "users";
    }
}
