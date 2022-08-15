package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Ticket.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 17.06.2022.
 */
public class Ticket {
    private int id;
    private MovieSession movieSession;
    private Seat seat;
    private User user;

    public Ticket() {

    }

    public Ticket(int id, MovieSession movieSession, Seat seat, User user) {
        this.id = id;
        this.movieSession = movieSession;
        this.seat = seat;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Objects.equals(movieSession, ticket.movieSession) && Objects.equals(seat, ticket.seat) && Objects.equals(user, ticket.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieSession, seat, user);
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", movieSession=" + movieSession
                + ", seat=" + seat
                + ", user=" + user
                + '}';
    }
}
