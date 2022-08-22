package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * UserController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 15.08.2022.
 */
@ThreadSafe
@Controller
public class UserController {
    private final UserService users;

    public UserController(UserService users) {
        this.users = users;
    }

    /**
     * Get all users.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return users page.
     */
    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("users", users.getAllUsers());
        return "users";
    }

    /**
     * Login page.
     *
     * @param model Model.
     * @param fail  boolean parameter.
     * @return login page.
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Login.
     *
     * @param user User.
     * @param req  HttpServletRequest.
     * @return index page if user is not null, or redirect to loginPage if null.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = users.findUserByEmailAndPhone(
                user.getEmail(), user.getPhone()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * Logout.
     * Invalidates HttpSession.
     *
     * @param session HttpSession.
     * @return redirect to loginPage.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

    /**
     * Registration form.
     *
     * @param model Model.
     * @param fail  boolean.
     * @return register page.
     */
    @GetMapping("/register")
    public String registerForm(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "register";
    }

    /**
     * Register.
     *
     * @param user User.
     * @param req  HttpServletRequest.
     * @return index if user is not null, or redirect to register page.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = users.addUser(user);
        if (userDb.isEmpty()) {
            return "redirect:/register?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * Returns user from HttpSession.
     *
     * @param model   Model.
     * @param session HttpSession.
     */
    private void getUserFromSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        model.addAttribute("user", user);
    }
}
