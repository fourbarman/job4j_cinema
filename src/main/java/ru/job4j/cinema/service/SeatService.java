package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.repository.SeatsRepository;

import java.util.List;
import java.util.Optional;

/**
 * SeatService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 07.07.2022.
 */
@ThreadSafe
@Service
public class SeatService {
    private final SeatsRepository seats;

    public SeatService(SeatsRepository seats) {
        this.seats = seats;
    }

    /**
     * getAll.
     *
     * @return all Seats.
     */
    public List<Seat> getAll() {
        return this.seats.getAll();
    }

    /**
     * getFreeSeatsByMovieSession.
     *
     * @param movieSessionId MovieSession id.
     * @return free seats according to MovieSession id.
     */
    public List<Seat> getFreeSeatsByMovieSession(int movieSessionId) {
        List<Seat> allSeats = seats.getAll();
        List<Seat> occupiedSeats = seats.getSeatsByMovieSession(movieSessionId);
        allSeats.removeAll(occupiedSeats);
        return allSeats;
    }

    /**
     * findSeatById
     *
     * @param id Seat id.
     * @return Seat by id.
     */
    public Optional<Seat> findSeatById(int id) {
        return this.seats.findSeatById(id);
    }
}
