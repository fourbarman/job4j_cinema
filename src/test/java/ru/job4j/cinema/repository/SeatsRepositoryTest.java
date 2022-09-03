package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * SeatsDBStoreTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.08.2022.
 */
@DataJpaTest
public class SeatsRepositoryTest {
    /**
     * Test getAll().
     */
    @Test
    public void whenGetAllSeats() {
        SeatsRepository seatsRepository = new SeatsRepository(new Main().loadPool());
        Seat seat = new Seat(0, 2, 1);
        Optional<Seat> addedSeat = seatsRepository.addSeat(seat);
        List<Seat> seats = seatsRepository.getAll();
        assertThat(addedSeat).isPresent();
        assertThat(addedSeat.get().getPosRow()).isEqualTo(seat.getPosRow());
        assertThat(seats).contains(addedSeat.get());
    }

    /**
     * Test addSeat().
     */
    @Test
    public void whenAddSeat() {
        SeatsRepository seatsRepository = new SeatsRepository(new Main().loadPool());
        Seat seat = new Seat(0, 4, 4);
        Optional<Seat> addedSeat = seatsRepository.addSeat(seat);
        assertThat(addedSeat).isPresent();
        assertThat(addedSeat.get().getCell()).isEqualTo(seat.getCell());
        assertThat(addedSeat.get().getPosRow()).isEqualTo(seat.getPosRow());
    }

    /**
     * Test findSeatById().
     */
    @Test
    public void whenFindSeatById() {
        SeatsRepository seatsRepository = new SeatsRepository(new Main().loadPool());
        Seat seat = new Seat(1, 2, 2);
        Optional<Seat> addedSeat = seatsRepository.addSeat(seat);
        assertThat(addedSeat).isPresent();
        assertThat(addedSeat.get().getPosRow()).isEqualTo(seat.getPosRow());
    }

    /**
     * Test getSeatsByMovieSession().
     */
    @Test
    public void whenGetSeatsByMovieSession() {
        BasicDataSource pool = new Main().loadPool();
        SeatsRepository seatsRepository = new SeatsRepository(pool);
        MovieSessionRepository movieSessionRepository = new MovieSessionRepository(pool);
        TicketRepository ticketRepository = new TicketRepository(pool);
        UserRepository userRepository = new UserRepository(pool);
        Optional<Seat> seat1 = seatsRepository.addSeat(new Seat(0, 3, 1));
        Optional<Seat> seat2 = seatsRepository.addSeat(new Seat(0, 3, 2));
        Optional<Seat> seat3 = seatsRepository.addSeat(new Seat(0, 3, 3));
        Optional<MovieSession> session = movieSessionRepository.addSession(new MovieSession(0, "Movie"));
        Optional<User> user = userRepository.addUser(new User(0, "username6", "email6", "phone6"));
        ticketRepository.addTicket(new Ticket(0, session.get(), seat1.get(), user.get()));
        ticketRepository.addTicket(new Ticket(0, session.get(), seat2.get(), user.get()));
        ticketRepository.addTicket(new Ticket(0, session.get(), seat3.get(), user.get()));
        assertThat(seatsRepository.getSeatsByMovieSession(session.get().getId())).contains(seat1.get(), seat2.get(), seat3.get());
    }
}
