package ru.job4j.cinema.repository;

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
 * TicketDBStoreTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.08.2022.
 */
@DataJpaTest
public class TicketRepositoryTest {
    /**
     * Test getAll().
     */
    @Test
    public void whenGetAll() {
        TicketRepository ticketRepository = new TicketRepository(new Main().loadPool());
        Optional<MovieSession> movieSession = new MovieSessionRepository(new Main().loadPool()).addSession(new MovieSession(10, "10"));
        assertThat(movieSession).isPresent();
        Optional<User> user = new UserRepository(new Main().loadPool()).addUser(new User(10, "10", "10", "10"));
        assertThat(user).isPresent();
        Optional<Seat> seat = new SeatsRepository(new Main().loadPool()).addSeat(new Seat(10, 10, 10));
        assertThat(seat).isPresent();
        Optional<Ticket> ticket1 = ticketRepository.addTicket(new Ticket(10, movieSession.get(), seat.get(), user.get()));
        List<Ticket> tickets = ticketRepository.getAll();
        assertThat(ticket1).isPresent();
        assertThat(tickets.size()).isEqualTo(1);
        assertThat(tickets).contains(ticket1.get());
    }

    /**
     * Test addTicket().
     */
    @Test
    public void whenAddTicket() {
        TicketRepository ticketRepository = new TicketRepository(new Main().loadPool());
        Optional<MovieSession> movieSession = new MovieSessionRepository(new Main().loadPool()).addSession(new MovieSession(20, "20"));
        assertThat(movieSession).isPresent();
        Optional<User> user = new UserRepository(new Main().loadPool()).addUser(new User(20, "20", "20", "20"));
        assertThat(user).isPresent();
        Optional<Seat> seat = new SeatsRepository(new Main().loadPool()).addSeat(new Seat(20, 20, 20));
        assertThat(seat).isPresent();
        Ticket ticket = new Ticket(20, movieSession.get(), seat.get(), user.get());
        Optional<Ticket> addedTicket = ticketRepository.addTicket(ticket);
        assertThat(addedTicket).isPresent();
        assertThat(addedTicket.get().getMovieSession()).isEqualTo(ticket.getMovieSession());
        assertThat(addedTicket.get().getUser()).isEqualTo(ticket.getUser());
    }

    /**
     * Test findTicketById().
     */
    @Test
    public void whenFindTicketById() {
        TicketRepository ticketRepository = new TicketRepository(new Main().loadPool());
        Optional<MovieSession> movieSession = new MovieSessionRepository(new Main().loadPool()).addSession(new MovieSession(30, "30"));
        assertThat(movieSession).isPresent();
        Optional<User> user = new UserRepository(new Main().loadPool()).addUser(new User(30, "30", "30", "30"));
        assertThat(user).isPresent();
        Optional<Seat> seat = new SeatsRepository(new Main().loadPool()).addSeat(new Seat(30, 30, 30));
        assertThat(seat).isPresent();
        Ticket ticket = new Ticket(30, movieSession.get(), seat.get(), user.get());
        Optional<Ticket> addedTicket = ticketRepository.addTicket(ticket);
        assertThat(addedTicket).isPresent();
        Optional<Ticket> foundTicket = ticketRepository.findTicketById(addedTicket.get().getId());
        assertThat(foundTicket).isPresent();
        assertThat(foundTicket.get()).isEqualTo(ticket);
    }
}
