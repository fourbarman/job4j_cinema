package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.persistence.MovieSessionDBStore;

import java.util.List;
import java.util.Optional;

/**
 * MovieSessionService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 07.07.2022.
 */
@ThreadSafe
@Service
public class MovieSessionService {
    private final MovieSessionDBStore movieSessions;

    public MovieSessionService(MovieSessionDBStore movieSessions) {
        this.movieSessions = movieSessions;
    }

    public List<MovieSession> getAllSessions() {
        return this.movieSessions.getAll();
    }

    public Optional<MovieSession> addSession(MovieSession movieSession) {
        return this.movieSessions.addSession(movieSession);
    }

    public Optional<MovieSession> findSessionById(int id) {
        return this.movieSessions.getSessionById(id);
    }

    public void updateSession(MovieSession movieSession) {
        this.movieSessions.updateSession(movieSession);
    }
}
