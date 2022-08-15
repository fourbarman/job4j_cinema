package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.service.MovieSessionService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * MovieSessionControllerTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.08.2022.
 */
public class MovieSessionControllerTest {
    /**
     * Test when movieSessions than success and return moviesessions page.
     */
    @Test
    void movieSessions() {
        List<MovieSession> movieSessions = List.of(
                new MovieSession(1, "1"),
                new MovieSession(2, "2")
        );
        Model model = mock(Model.class);
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        when(movieSessionService.getAllSessions()).thenReturn(movieSessions);
        MovieSessionController movieSessionController = new MovieSessionController(movieSessionService);
        HttpSession httpSession = new MockHttpSession();
        String page = movieSessionController.movieSessions(model, httpSession);
        verify(model).addAttribute("moviesessions", movieSessions);
        assertThat(page).isEqualTo("moviesessions");
    }

    /**
     * Test when chooseMovieSessions than success and return chooseMovieSession pager.
     */
    @Test
    void chooseMovieSessions() {
        List<MovieSession> movieSessions = List.of(
                new MovieSession(1, "1"),
                new MovieSession(2, "2")
        );
        Model model = mock(Model.class);
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        when(movieSessionService.getAllSessions()).thenReturn(movieSessions);
        MovieSessionController movieSessionController = new MovieSessionController(movieSessionService);
        HttpSession httpSession = new MockHttpSession();
        String page = movieSessionController.chooseMovieSessions(model, httpSession);
        verify(model).addAttribute("moviesessions", movieSessions);
        assertThat(page).isEqualTo("chooseMovieSession");
    }

    /**
     * Test when retainMovieSessions than return chooseSeat?moviesessionId with MovieSession Id page.
     */
    @Test
    void retainMovieSessions() {
        MovieSessionService movieSessionService = mock(MovieSessionService.class);
        MovieSessionController movieSessionController = new MovieSessionController(movieSessionService);
        String page = movieSessionController.retainMovieSessions(1);
        assertThat(page).isEqualTo("redirect:/chooseSeat?moviesessionId=1");
    }
}
