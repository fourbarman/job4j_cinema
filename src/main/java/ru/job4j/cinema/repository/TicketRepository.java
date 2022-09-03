package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.MovieSession;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TicketDBStore.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 07.07.2022.
 */
@Repository
public class TicketRepository {
    private final BasicDataSource pool;
    private static final String SELECT_ALL = """
                    SELECT t.id AS ticket_id,
                           sess.id AS session_id,
                           sess.session_name AS session_name,
                           u.id AS user_id,
                           u.username AS username,
                           u.email AS user_email,
                           u.phone AS user_phone,
                           s.id AS seat_id,
                           s.pos_row AS seat_pos_row,
                           s.cell AS seat_cell
                    FROM tickets t
                    INNER JOIN sessions sess ON t.session_id = sess.id
                    INNER JOIN users u ON t.user_id = u.id
                    INNER JOIN seats s ON t.seats_id = s.id;
                    """;
    private static final String INSERT_TICKET = "INSERT INTO tickets(session_id, seats_id, user_id) VALUES (?, ?, ?);";
    private static final String SELECT_TICKET_BY_ID = """
                    SELECT t.id AS ticket_id,
                           sess.id AS session_id,
                           sess.session_name AS session_name,
                           u.id AS user_id,
                           u.username AS username,
                           u.email AS user_email,
                           u.phone AS user_phone,
                           s.id AS seat_id,
                           s.pos_row AS seat_pos_row,
                           s.cell AS seat_cell
                    FROM tickets t
                         INNER JOIN sessions sess ON t.session_id = sess.id
                         INNER JOIN users u ON t.user_id = u.id
                         INNER JOIN seats s ON t.seats_id = s.id
                    WHERE t.id = ?;
                    """;

    public TicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     *
     * @return List.
     */
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(getTicketFromRS(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return tickets;
    }

    /**
     * addTicket.
     *
     * @param ticket Ticket.Ticket
     * @return Optional.
     */
    public Optional<Ticket> addTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(INSERT_TICKET, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getMovieSession().getId());
            ps.setInt(2, ticket.getSeat().getId());
            ps.setInt(3, ticket.getUser().getId());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
            }
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
        return Optional.of(ticket);
    }

    /**
     * findTicketById
     *
     * @param id Id.
     * @return Optional.
     */
    public Optional<Ticket> findTicketById(int id) {
        Ticket ticket = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_TICKET_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket = getTicketFromRS(rs);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(ticket);
    }

    /**
     * Return Ticket from ResultSet.
     *
     * @param resultSet ResultSet.
     * @return Ticket.
     * @throws SQLException Exception.
     */
    private Ticket getTicketFromRS(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getInt("ticket_id"),
                new MovieSession(
                        resultSet.getInt("session_id"),
                        resultSet.getString("session_name")),
                new Seat(
                        resultSet.getInt("seat_id"),
                        resultSet.getInt("seat_pos_row"),
                        resultSet.getInt("seat_cell")),
                new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_phone"))
        );
    }
}
