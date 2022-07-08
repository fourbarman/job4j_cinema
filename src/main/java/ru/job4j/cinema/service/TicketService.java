package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.TicketDBStore;

import java.util.List;
import java.util.Optional;

/**
 * TicketService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 07.07.2022.
 */
@ThreadSafe
@Service
public class TicketService {
    private final TicketDBStore tickets;

    public TicketService(TicketDBStore tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getAllTickets() {
        return this.tickets.getAll();
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return this.tickets.addTicket(ticket);
    }

    public Optional<Ticket> findTicketById(int id) {
        return this.tickets.findTicketById(id);
    }

    public void updateTicket(Ticket ticket) {
        this.tickets.updateTicket(ticket);
    }
}
