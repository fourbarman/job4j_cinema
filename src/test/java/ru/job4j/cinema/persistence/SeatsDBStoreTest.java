package ru.job4j.cinema.persistence;

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
public class SeatsDBStoreTest {
    /**
     * Test getAll().
     */
    @Test
    public void whenGetAllSeats() {
        SeatsDBStore seatsDBStore = new SeatsDBStore(new Main().loadPool());
        Seat seat = new Seat(0, 2, 1);
        Optional<Seat> addedSeat = seatsDBStore.addSeat(seat);
        List<Seat> seats = seatsDBStore.getAll();
        assertThat(addedSeat).isPresent();
        assertThat(addedSeat.get().getPosRow()).isEqualTo(seat.getPosRow());
        assertThat(seats).contains(addedSeat.get());
    }

    /**
     * Test addSeat().
     */
    @Test
    public void whenAddSeat() {
        SeatsDBStore seatsDBStore = new SeatsDBStore(new Main().loadPool());
        Seat seat = new Seat(0, 4, 4);
        Optional<Seat> addedSeat = seatsDBStore.addSeat(seat);
        assertThat(addedSeat).isPresent();
        assertThat(addedSeat.get().getCell()).isEqualTo(seat.getCell());
        assertThat(addedSeat.get().getPosRow()).isEqualTo(seat.getPosRow());
    }

    /**
     * Test findSeatById().
     */
    @Test
    public void whenFindSeatById() {
        SeatsDBStore seatsDBStore = new SeatsDBStore(new Main().loadPool());
        Seat seat = new Seat(1, 2, 2);
        Optional<Seat> addedSeat = seatsDBStore.addSeat(seat);
        assertThat(addedSeat).isPresent();
        assertThat(addedSeat.get().getPosRow()).isEqualTo(seat.getPosRow());
    }

    /**
     * Test getSeatsByMovieSession().
     */
    @Test
    public void whenGetSeatsByMovieSession() {
        BasicDataSource pool = new Main().loadPool();
        SeatsDBStore seatsDBStore = new SeatsDBStore(pool);
        MovieSessionDBStore movieSessionDBStore = new MovieSessionDBStore(pool);
        TicketDBStore ticketDBStore = new TicketDBStore(pool);
        UserDBStore userDBStore = new UserDBStore(pool);
        Optional<Seat> seat1 = seatsDBStore.addSeat(new Seat(0, 3, 1));
        Optional<Seat> seat2 = seatsDBStore.addSeat(new Seat(0, 3, 2));
        Optional<Seat> seat3 = seatsDBStore.addSeat(new Seat(0, 3, 3));
        Optional<MovieSession> session = movieSessionDBStore.addSession(new MovieSession(0, "Movie"));
        Optional<User> user = userDBStore.addUser(new User(0, "username6", "email6", "phone6"));
        ticketDBStore.addTicket(new Ticket(0, session.get(), seat1.get(), user.get()));
        ticketDBStore.addTicket(new Ticket(0, session.get(), seat2.get(), user.get()));
        ticketDBStore.addTicket(new Ticket(0, session.get(), seat3.get(), user.get()));
        assertThat(seatsDBStore.getSeatsByMovieSession(session.get().getId())).contains(seat1.get(), seat2.get(), seat3.get());
    }
}
