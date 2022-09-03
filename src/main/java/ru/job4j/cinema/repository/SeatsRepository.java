package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SeatsDBStore.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 06.07.2022.
 */
@Repository
public class SeatsRepository {
    private final BasicDataSource pool;
    private static final String SELECT_ALL = "SELECT * FROM seats;";
    private static final String SELECT_SEAT_BY_MOVIE_SESSION = """
            SELECT s.id, s.pos_row, s.cell
            FROM seats s
            INNER JOIN tickets t
            ON s.id = t.seats_id AND session_id = ?;
            """;
    private static final String INSERT_SEAT = "INSERT INTO seats(pos_row, cell) VALUES(?, ?);";
    private static final String SELECT_SEAT_BY_ID = "SELECT * FROM seats WHERE id = ?;";

    public SeatsRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     *
     * @return List.
     */
    public List<Seat> getAll() {
        List<Seat> seats = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seats.add(getSeatFromRS(rs));
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return seats;
    }

    /**
     * getFreeSeatsByMovieSession.
     *
     * @return List of all free seats counted by movieSession id.
     */
    public List<Seat> getSeatsByMovieSession(int movieSessionId) {
        List<Seat> occupiedSeats = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_SEAT_BY_MOVIE_SESSION)) {
            ps.setInt(1, movieSessionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    occupiedSeats.add(getSeatFromRS(rs));
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return occupiedSeats;
    }

    /**
     * addSeat.
     *
     * @param seat Seat.
     * @return Optional.
     */
    public Optional<Seat> addSeat(Seat seat) {
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(INSERT_SEAT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, seat.getPosRow());
            ps.setInt(2, seat.getCell());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    seat.setId(rs.getInt(1));
                }
            }
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
        return Optional.of(seat);
    }

    /**
     * findSeatById
     *
     * @param id Id.
     * @return Optional.
     */
    public Optional<Seat> findSeatById(int id) {
        Seat seat = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_SEAT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    seat = getSeatFromRS(rs);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(seat);
    }

    /**
     * Return Seat from ResultSet.
     *
     * @param resultSet ResultSet.
     * @return Seat.
     * @throws SQLException Exception.
     */
    private Seat getSeatFromRS(ResultSet resultSet) throws SQLException {
        return new Seat(
                resultSet.getInt("id"),
                resultSet.getInt("pos_row"),
                resultSet.getInt("cell")
        );
    }
}
