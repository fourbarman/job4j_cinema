package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.service.MovieSessionService;
import ru.job4j.cinema.service.SeatService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeatControllerTest {
    @Test
    void seats() {
        List<Seat> seats = List.of(
                new Seat(1, 1, 1),
                new Seat(2, 2, 2),
                new Seat(3, 3, 3)
        );
        SeatService seatService = mock(SeatService.class);
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        when(seatService.getAll()).thenReturn(seats);
        Model model = mock(Model.class);
        HttpSession httpSession = new MockHttpSession();
        SeatController seatController = new SeatController(seatService, movieSessionService);
        String page = seatController.seats(model, httpSession);
        verify(model).addAttribute("seats", seats);
        assertThat(page).isEqualTo("seats");
    }

    @Test
    void chooseSeat() {
        List<Seat> seats = List.of(
                new Seat(1, 1, 1),
                new Seat(2, 2, 2));
        SeatService seatService = mock(SeatService.class);
        when(seatService.getFreeSeatsByMovieSession(1)).thenReturn(seats);
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        MovieSession movieSession = new MovieSession(1, "1");
        when(movieSessionService.findSessionById(1)).thenReturn(Optional.of(movieSession));
        Model model = mock(Model.class);
        HttpSession httpSession = new MockHttpSession();
        SeatController seatController = new SeatController(seatService, movieSessionService);
        String page = seatController.chooseSeat(model, httpSession, 1);
        verify(model).addAttribute("seats", seats);
        verify(model).addAttribute("moviesession", movieSession);
        assertThat(page).isEqualTo("chooseSeat");
    }
}
