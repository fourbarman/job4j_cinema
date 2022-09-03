package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.MovieSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * MovieSessionDBStore test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 22.08.2022.
 */
@DataJpaTest
public class MovieSessionRepositoryTest {
    /**
     * Test getAll().
     */
    @Test
    public void whenGetAll() {
        MovieSessionRepository movieSessionRepository = new MovieSessionRepository(new Main().loadPool());
        Optional<MovieSession> session1 = movieSessionRepository.addSession(new MovieSession(1, "session1"));
        Optional<MovieSession> session2 = movieSessionRepository.addSession(new MovieSession(2, "session2"));
        assertThat(session1).isPresent();
        assertThat(session2).isPresent();
        assertThat(movieSessionRepository.getAll()).contains(session1.get(), session2.get());
    }

    /**
     * Test add().
     */
    @Test
    public void whenAdd() {
        MovieSessionRepository movieSessionRepository = new MovieSessionRepository(new Main().loadPool());
        MovieSession session = new MovieSession(1, "session3");
        Optional<MovieSession> addedSession = movieSessionRepository.addSession(session);
        assertThat(addedSession).isPresent();
        assertThat(addedSession.get().getName()).isEqualTo(session.getName());
    }

    /**
     * Test getSessionById().
     */
    @Test
    public void whenGetSessionById() {
        MovieSessionRepository movieSessionRepository = new MovieSessionRepository(new Main().loadPool());
        MovieSession session = new MovieSession(1, "session4");
        Optional<MovieSession> addedSession = movieSessionRepository.addSession(session);
        assertThat(addedSession).isPresent();
        Optional<MovieSession> foundSession = movieSessionRepository.getSessionById(addedSession.get().getId());
        assertThat(foundSession).isPresent();
        assertThat(foundSession.get()).isEqualTo(addedSession.get());
    }
}
