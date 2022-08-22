package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.MovieSessionService;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * TicketControllerTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.08.2022.
 */
public class TicketControllerTest {
    /**
     * Test when tickets than success and return tickets page.
     */
    @Test
    void whenTickets() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        List<Ticket> tickets = List.of(
                new Ticket(1, new MovieSession(), new Seat(), new User()),
                new Ticket(2, new MovieSession(), new Seat(), new User())
        );
        when(ticketService.getAllTickets()).thenReturn(tickets);
        Model model = mock(Model.class);
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.tickets(model, new MockHttpSession());
        verify(model).addAttribute("tickets", tickets);
        assertThat(page).isEqualTo("tickets");
    }

    /**
     * Test when order than success and return page orderTicket.
     */
    @Test
    void whenOrder() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        List<MovieSession> movieSessions = List.of(
                new MovieSession(),
                new MovieSession()
        );
        List<Seat> seats = List.of(
                new Seat(),
                new Seat()
        );
        when(movieSessionService.getAllSessions()).thenReturn(movieSessions);
        when(seatService.getAll()).thenReturn(seats);
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.order(model, new MockHttpSession());
        verify(model).addAttribute("moviesessions", movieSessions);
        verify(model).addAttribute("seats", seats);
        assertThat(page).isEqualTo("orderTicket");
    }

    /**
     * Test when ticketOrder than success and return page redirect:/success?ticketId=id
     */
    @Test
    void placeTicketOrder() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        User user = new User(1, "1", "1", "1");
        MovieSession movieSession = new MovieSession(1, "1");
        Seat seat = new Seat(1, 1, 1);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);
        when(movieSessionService.findSessionById(1)).thenReturn(Optional.of(movieSession));
        when(seatService.findSeatById(1)).thenReturn(Optional.of(seat));
        when(userService.findUserById(1)).thenReturn(Optional.of(user));
        Ticket ticket = new Ticket(0, movieSession, seat, user);
        when(ticketService.addTicket(ticket)).thenReturn(Optional.of(ticket));
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.placeTicketOrder(model, 1, 1, httpSession);
        assertThat(page).isEqualTo("redirect:/success?ticketId=0");
    }

    /**
     * Test when success and return success page.
     */
    @Test
    void whenSuccess() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        Ticket ticket = new Ticket(1, new MovieSession(), new Seat(), new User());
        when(ticketService.findTicketById(1)).thenReturn(Optional.of(ticket));
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.success(model, 1, new MockHttpSession());
        verify(model).addAttribute("ticket", ticket);
        assertThat(page).isEqualTo("success");
    }

    /**
     * Test when fail and return fail page.
     */
    @Test
    void whenFail() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        Seat seat = new Seat(1, 1, 1);
        when(seatService.findSeatById(1)).thenReturn(Optional.of(seat));
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.fail(model, 1, new MockHttpSession());
        verify(model).addAttribute("seat", seat);
        assertThat(page).isEqualTo("fail");
    }

    /**
     * Test when order than success and return order page.
     */
    @Test
    void whenTestOrderThanSuccess() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        MovieSession movieSession = new MovieSession();
        Seat seat = new Seat();
        when(movieSessionService.findSessionById(1)).thenReturn(Optional.of(movieSession));
        when(seatService.findSeatById(1)).thenReturn(Optional.of(seat));
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.order(model, 1, 1, new MockHttpSession());
        verify(model).addAttribute("moviesession", movieSession);
        verify(model).addAttribute("seat", seat);
        assertThat(page).isEqualTo("order");
    }

    /**
     * Test when placeOrder than success and return page redirect:/success?ticketId=id.
     */
    @Test
    void whenPlaceOrderThanSuccess() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        User user = new User(1, "1", "1", "1");
        MovieSession movieSession = new MovieSession(1, "1");
        Seat seat = new Seat(1, 1, 1);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);
        when(movieSessionService.findSessionById(1)).thenReturn(Optional.of(movieSession));
        when(seatService.findSeatById(1)).thenReturn(Optional.of(seat));
        Ticket ticket = new Ticket(0, movieSession, seat, user);
        when(ticketService.addTicket(ticket)).thenReturn(Optional.of(ticket));
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.placeOrder(model, 1, 1, httpSession);
        assertThat(page).isEqualTo("redirect:/success?ticketId=0");
    }

    /**
     * Test when placeOrder than fail and return redirect:/fail page.
     */
    @Test
    public void whenPlaceOrderThanFail() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        SeatService seatService = mock(SeatService.class);
        UserService userService = mock(UserService.class);
        TicketService ticketService = mock(TicketService.class);
        Model model = mock(Model.class);
        User user = new User(1, "1", "1", "1");
        MovieSession movieSession = new MovieSession(1, "1");
        Seat seat = new Seat(1, 1, 1);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);
        when(movieSessionService.findSessionById(1)).thenReturn(Optional.of(movieSession));
        when(seatService.findSeatById(1)).thenReturn(Optional.of(seat));
        Ticket ticket = new Ticket(0, movieSession, seat, user);
        when(ticketService.addTicket(ticket)).thenReturn(Optional.empty());
        TicketController ticketController = new TicketController(ticketService, movieSessionService, userService, seatService);
        String page = ticketController.placeOrder(model, 1, 1, httpSession);
        assertThat(page).isEqualTo("redirect:/fail");
    }
}
