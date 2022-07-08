package ru.job4j.cinema.persistence;

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
public class TicketDBStore {
    private BasicDataSource pool;

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     * @return List.
     */
    public List<Ticket> getAll() {
        String query = "SELECT t.id AS ticket_id, " +
                "       sess.id AS session_id, " +
                "       sess.session_name AS session_name, " +
                "       u.id AS user_id, " +
                "       u.username AS username, " +
                "       u.email AS user_email, " +
                "       u.phone AS user_phone, " +
                "       s.id AS seat_id," +
                "       s.pos_row AS seat_pos_row, " +
                "       s.cell AS seat_cell " +
                "FROM ticket t, " +
                "     sessions sess, " +
                "     users u,  " +
                "     seats s " +
                "WHERE t.seats_id = s.id AND " +
                "      t.session_id = sess.id AND " +
                "      t.user_id = u.id";
        List<Ticket> tickets = new ArrayList<>();
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    tickets.add(new Ticket(
                            rs.getInt("ticket_id"),
                            new MovieSession(rs.getInt("session_id"),
                                    rs.getString("session_name")),
                            new Seat(rs.getInt("seat_id"),
                                    rs.getInt("seat_pos_row"),
                                    rs.getInt("seat_cell")),
                            new User(rs.getInt("user_id"),
                                    rs.getString("username"),
                                    rs.getString("user_email"),
                                    rs.getString("user_phone"))
                    ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return tickets;
    }

    /**
     * addTicket.
     * @param ticket Ticket.
     * @return Optional.
     */
    public Optional<Ticket> addTicket(Ticket ticket) {
        String query = "INSERT INTO ticket(session_id, seats_id, user_id) " +
                "VALUES (?, ?, ?)";
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getMovieSession().getId());
            ps.setInt(2, ticket.getSeat().getId());
            ps.setInt(3, ticket.getUser().getId());
            ps.executeQuery();
            try(ResultSet rs = ps.getGeneratedKeys()) {
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
     * @param id Id.
     * @return Optional.
     */
    public Optional<Ticket> findTicketById(int id) {
        String query = "SELECT t.id AS ticket_id, " +
                "       sess.id AS session_id, " +
                "       sess.session_name AS session_name, " +
                "       u.id AS user_id, " +
                "       u.username AS username, " +
                "       u.email AS user_email, " +
                "       u.phone AS user_phone, " +
                "       s.id AS seat_id," +
                "       s.pos_row AS seat_pos_row, " +
                "       s.cell AS seat_cell " +
                "FROM ticket t, " +
                "     sessions sess, " +
                "     users u,  " +
                "     seats s " +
                "WHERE t.seats_id = s.id AND " +
                "      t.session_id = sess.id AND " +
                "      t.user_id = u.id AND t.id = ?";
        Ticket ticket = null;
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    ticket = new Ticket(
                            rs.getInt("ticket_id"),
                            new MovieSession(rs.getInt("session_id"),
                                    rs.getString("session_name")),
                            new Seat(rs.getInt("seat_id"),
                                    rs.getInt("seat_pos_row"),
                                    rs.getInt("seat_cell")),
                            new User(rs.getInt("user_id"),
                                    rs.getString("username"),
                                    rs.getString("user_email"),
                                    rs.getString("user_phone"))
                    );
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(ticket);
    }

    /**
     * updateTicket
     * @param ticket Ticket.
     */
    public void updateTicket(Ticket ticket) {
        String query = "UPDATE ticket SET " +
                "session_id = ?, " +
                "seats_id = ?, " +
                "user_id = ? " +
                "WHERE " +
                "ticket.id = ?";
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, ticket.getMovieSession().getId());
            ps.setInt(2, ticket.getSeat().getId());
            ps.setInt(3, ticket.getUser().getId());
            ps.setInt(4, ticket.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
