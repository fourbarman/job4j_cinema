package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * IndexController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 15.08.2022.
 */
@ThreadSafe
@Controller
public class IndexController {
    /**
     * Index.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return index.
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "index";
    }
}
