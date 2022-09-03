package ru.job4j.cinema.repository;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.MovieSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MovieSessionDBStore.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 06.07.2022.
 */
@Repository
public class MovieSessionRepository {
    private final BasicDataSource pool;
    private static final String SELECT_ALL = "SELECT * FROM sessions;";
    private static final String INSERT_SESSION = "INSERT INTO sessions(session_name) VALUES (?);";
    private static final String SELECT_SESSION_BY_ID = "SELECT * FROM sessions WHERE id = ?;";

    public MovieSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     *
     * @return List.
     */
    public List<MovieSession> getAll() {
        List<MovieSession> sessionList = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sessionList.add(getSessionFromRS(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return sessionList;
    }

    /**
     * addSession.
     *
     * @param session MovieSession.
     * @return Optional.
     */
    public Optional<MovieSession> addSession(MovieSession session) {
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(INSERT_SESSION, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(
                            rs.getInt(1)
                    );
                }
            }
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
        return Optional.of(session);
    }

    /**
     * getSessionById
     *
     * @param id id.
     * @return Optional.
     */
    public Optional<MovieSession> getSessionById(int id) {
        MovieSession session = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_SESSION_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    session = getSessionFromRS(rs);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(session);
    }

    /**
     * Return MovieSession from ResultSet.
     *
     * @param resultSet ResultSet.
     * @return MovieSession.
     * @throws SQLException Exception.
     */
    private MovieSession getSessionFromRS(ResultSet resultSet) throws SQLException {
        return new MovieSession(
                resultSet.getInt("id"),
                resultSet.getString("session_name")
        );
    }
}
