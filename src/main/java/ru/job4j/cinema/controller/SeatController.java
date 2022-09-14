package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.service.MovieSessionService;
import ru.job4j.cinema.service.SeatService;

import javax.servlet.http.HttpSession;

/**
 * SeatController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 15.08.2022.
 */
@ThreadSafe
@Controller
public class SeatController {
    private final SeatService seats;
    private final MovieSessionService moviesessions;

    public SeatController(SeatService seats, MovieSessionService moviesessions) {
        this.seats = seats;
        this.moviesessions = moviesessions;
    }

    /**
     * Seats.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return seats page.
     */
    @GetMapping("/seats")
    public String seats(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("seats", this.seats.getAll());
        return "seats";
    }

    /**
     * ChooseSeat.
     *
     * @param model          Model.
     * @param session        HttpSession.
     * @param moviesessionId MovieSession id.
     * @return chooseSeat page.
     */
    @GetMapping("/chooseSeat")
    public String chooseSeat(Model model, HttpSession session, @RequestParam("moviesessionId") int moviesessionId) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("moviesession", this.moviesessions.findSessionById(moviesessionId).get());
        model.addAttribute("seats", this.seats.getFreeSeatsByMovieSession(moviesessionId));
        return "chooseSeat";
    }

    /**
     * SendSeat.
     *
     * @param seatId Seat id.
     * @return redirect orderTicket page with seat id.
     */
    @PostMapping("/sendSeat")
    public String sendSeat(@RequestParam("seat.id") int seatId) {
        return "redirect:/orderTicket?seatId=" + seatId;
    }
}
