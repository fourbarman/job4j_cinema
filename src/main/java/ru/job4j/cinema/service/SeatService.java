package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.persistence.SeatsDBStore;

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
    private final SeatsDBStore seats;
    public SeatService(SeatsDBStore seats) {
        this.seats = seats;
    }

    public List<Seat> gelAll() {
        return this.seats.getAll();
    }

    public Optional<Seat> addSeat(Seat seat) {
        return this.seats.addSeat(seat);
    }

    public Optional<Seat> findSeatById(int id) {
        return this.seats.findSeatById(id);
    }

    public void updateSeat(Seat seat) {
        this.seats.updateSeat(seat);
    }
}
