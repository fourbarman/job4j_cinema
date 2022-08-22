package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.MovieSessionService;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * TicketController.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 15.08.2022.
 */
@ThreadSafe
@Controller
public class TicketController {
    private final TicketService tickets;
    private final MovieSessionService moviesessions;
    private final UserService users;
    private final SeatService seats;

    public TicketController(TicketService tickets, MovieSessionService moviesessions, UserService users, SeatService seats) {
        this.tickets = tickets;
        this.moviesessions = moviesessions;
        this.users = users;
        this.seats = seats;
    }

    /**
     * Tickets.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return tickets page.
     */
    @GetMapping("/tickets")
    public String tickets(Model model, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("tickets", this.tickets.getAllTickets());
        return "tickets";
    }

    /**
     * Order.
     *
     * @param model   Model.
     * @param session HttpSession.
     * @return orderTicket page.
     */
    @GetMapping("/orderTicket")
    public String order(Model model, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("moviesessions", this.moviesessions.getAllSessions());
        model.addAttribute("seats", this.seats.getAll());
        return "orderTicket";
    }

    /**
     * placeTicketOrder.
     *
     * @param model          Model.
     * @param moviesessionId MovieSession id.
     * @param seatId         Seat id.
     * @param session        HttpSession.
     * @return redirect success page if ticket is added successfully or fail page if not added.
     */
    @PostMapping("/placeTicketOrder")
    public String placeTicketOrder(
            Model model,
            @RequestParam("moviesession.id") int moviesessionId,
            @RequestParam("seat.id") int seatId,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        Ticket ticket = new Ticket(
                0,
                moviesessions.findSessionById(moviesessionId).get(),
                seats.findSeatById(seatId).get(),
                users.findUserById(user.getId()).get()
        );
        Optional<Ticket> addedTicket = tickets.addTicket(ticket);
        if (addedTicket.isEmpty()) {
            return "redirect:/fail?formSeatId=" + seatId;
        }
        return "redirect:/success?ticketId=" + addedTicket.get().getId();
    }

    /**
     * Success.
     *
     * @param model    Model.
     * @param ticketId Ticket id.
     * @param session  MovieSession id.
     * @return success page.
     */
    @GetMapping("/success")
    public String success(Model model, @RequestParam("ticketId") int ticketId, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("ticket", tickets.findTicketById(ticketId).get());
        return "success";
    }

    /**
     * Fail.
     *
     * @param model      Model.
     * @param formSeatId Seat id.
     * @param session    HttpSession.
     * @return fail page.
     */
    @GetMapping("/fail")
    public String fail(Model model, @RequestParam("formSeatId") int formSeatId, HttpSession session) {
        getUserFromSession(model, session);
        model.addAttribute("seat", seats.findSeatById(formSeatId).get());
        return "fail";
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

    /**
     * Order.
     *
     * @param model          Model.
     * @param moviesessionId MovieSession id.
     * @param seatId         Seat id.
     * @param session        HttpSession.
     * @return order page.
     */
    @GetMapping("/order")
    public String order(Model model, @RequestParam("moviesessionId") int moviesessionId,
                        @RequestParam("seatId") int seatId, HttpSession session) {
        getUserFromSession(model, session);
        Optional<MovieSession> movie = this.moviesessions.findSessionById(moviesessionId);
        Optional<Seat> seat = this.seats.findSeatById(seatId);
        if (movie.isPresent() && seat.isPresent()) {
            model.addAttribute("moviesession", movie.get());
            model.addAttribute("seat", seat.get());
        }
        return "order";
    }

    /**
     * PlaceOrder.
     *
     * @param model          Model.
     * @param moviesessionId MovieSession id.
     * @param seatId         Seat id.
     * @param httpSession    HttpSession.
     * @return redirect success if ticket is added successfully or fail page if not added.
     */
    @PostMapping("/order")
    public String placeOrder(Model model, @RequestParam("moviesessionId") int moviesessionId,
                             @RequestParam("seatId") int seatId,
                             HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        MovieSession movie = moviesessions.findSessionById(moviesessionId).get();
        Seat seat = seats.findSeatById(seatId).get();
        Optional<Ticket> ticket = this.tickets.addTicket(new Ticket(0, movie, seat, user));
        if (ticket.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success?ticketId=" + ticket.get().getId();
    }
}
