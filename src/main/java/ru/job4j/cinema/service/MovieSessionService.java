package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.repository.MovieSessionRepository;

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
    private final MovieSessionRepository movieSessions;

    public MovieSessionService(MovieSessionRepository movieSessions) {
        this.movieSessions = movieSessions;
    }

    /**
     * getAllSessions.
     *
     * @return all MovieSessions.
     */
    public List<MovieSession> getAllSessions() {
        return this.movieSessions.getAll();
    }

    /**
     * findSessionById
     *
     * @param id MovieSession id.
     * @return Movie session by id.
     */
    public Optional<MovieSession> findSessionById(int id) {
        return this.movieSessions.getSessionById(id);
    }
}
