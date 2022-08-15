package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.MovieSessionService;

import javax.servlet.http.HttpSession;

/**
 * MovieSessionController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 15.08.2022.
 */
@ThreadSafe
@Controller
public class MovieSessionController {
    private final MovieSessionService movieSessions;

    public MovieSessionController(MovieSessionService movieSessions) {
        this.movieSessions = movieSessions;
    }

    /**
     * Get all movieSessions.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return moviesession page.
     */
    @GetMapping("/moviesessions")
    public String movieSessions(Model model, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("moviesessions", this.movieSessions.getAllSessions());
        return "moviesessions";
    }

    /**
     * Choose movieSession.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return chooseMovieSession page.
     */
    @GetMapping("/chooseMovieSession")
    public String chooseMovieSessions(Model model, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("moviesessions", this.movieSessions.getAllSessions());
        return "chooseMovieSession";
    }

    /**
     * Get movieSession by Id from form.
     *
     * @param moviesessionId Id.
     * @return redirect:/chooseSeat?moviesessionId= page
     */
    @PostMapping("/chooseMovieSession")
    public String retainMovieSessions(@RequestParam("moviesessionId") int moviesessionId) {
        return "redirect:/chooseSeat?moviesessionId=" + moviesessionId;
    }

    /**
     * Get user from HttpSession.
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