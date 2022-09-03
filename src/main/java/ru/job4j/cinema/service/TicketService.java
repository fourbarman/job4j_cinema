package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

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
    private final TicketRepository tickets;

    public TicketService(TicketRepository tickets) {
        this.tickets = tickets;
    }

    /**
     * getAllTickets.
     *
     * @return all tickets.
     */
    public List<Ticket> getAllTickets() {
        return this.tickets.getAll();
    }

    /**
     * addTicket.
     *
     * @param ticket Ticket.
     * @return added Ticket.
     */
    public Optional<Ticket> addTicket(Ticket ticket) {
        return this.tickets.addTicket(ticket);
    }

    /**
     * findTicketById.
     *
     * @param id Ticket id.
     * @return found Ticket.
     */
    public Optional<Ticket> findTicketById(int id) {
        return this.tickets.findTicketById(id);
    }
}
