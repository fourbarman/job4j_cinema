package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * TicketDBStoreTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.08.2022.
 */
public class TicketDBStoreTest {
    static BasicDataSource pool;
    static SeatsDBStore seatsDBStore;
    static MovieSessionDBStore movieSessionDBStore;
    static TicketDBStore ticketDBStore;
    static UserDBStore userDBStore;
    static MovieSession session;
    static User user;
    static Seat seat1;
    static Seat seat2;
    static Seat seat3;
    static Ticket ticket1;
    static Ticket ticket2;

    @BeforeAll
    static void initVars() {
        pool = new Main().loadPool();
        seatsDBStore = new SeatsDBStore(pool);
        movieSessionDBStore = new MovieSessionDBStore(pool);
        ticketDBStore = new TicketDBStore(pool);
        userDBStore = new UserDBStore(pool);
        seat1 = seatsDBStore.addSeat(new Seat(0, 4, 1)).get();
        seat2 = seatsDBStore.addSeat(new Seat(0, 4, 2)).get();
        seat3 = seatsDBStore.addSeat(new Seat(0, 4, 3)).get();
        session = movieSessionDBStore.addSession(new MovieSession(0, "Movies")).get();
        user = userDBStore.addUser(new User(0, "username7", "email7", "phone7")).get();
        ticket1 = ticketDBStore.addTicket(new Ticket(0, session, seat1, user)).get();
        ticket2 = ticketDBStore.addTicket(new Ticket(0, session, seat2, user)).get();
    }

    /**
     * Test getAll().
     */
    @Test
    public void whenGetAll() {
        List<Ticket> tickets = ticketDBStore.getAll();
        assertThat(tickets.size()).isEqualTo(2);
        assertThat(tickets).contains(ticket1, ticket2);
    }

    /**
     * Test addTicket().
     */
    @Test
    public void whenAddTicket() {
        Ticket ticket = new Ticket(0, session, seat3, user);
        Optional<Ticket> addedTicket = ticketDBStore.addTicket(ticket);
        assertThat(addedTicket).isPresent();
        assertThat(addedTicket.get().getMovieSession()).isEqualTo(ticket.getMovieSession());
        assertThat(addedTicket.get().getUser()).isEqualTo(ticket.getUser());
    }

    /**
     * Test findTicketById().
     */
    @Test
    public void whenFindTicketById() {
        Optional<Ticket> foundTicket = ticketDBStore.findTicketById(ticket1.getId());
        assertThat(foundTicket).isPresent();
        assertThat(foundTicket.get()).isEqualTo(ticket1);
    }
}
