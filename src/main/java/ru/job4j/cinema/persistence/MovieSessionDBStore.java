package ru.job4j.cinema.persistence;


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
public class MovieSessionDBStore {
    private final BasicDataSource pool;

    public MovieSessionDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     * @return List.
     */
    public List<MovieSession> getAll() {
        String query = "SELECT * FROM sessions";
        List<MovieSession> sessionList = new ArrayList<>();
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    sessionList.add(new MovieSession
                            (
                                    rs.getInt("id"),
                                    rs.getString("session_name")
                            ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return sessionList;
    }

    /**
     * addSession.
     * @param session MovieSession.
     * @return Optional.
     */
    public Optional<MovieSession> addSession(MovieSession session) {
        String query = "INSERT INTO sessions(session_name) VALUES (?)";
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.executeQuery();
            try(ResultSet rs = ps.getGeneratedKeys()) {
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
     * @param id id.
     * @return Optional.
     */
    public Optional<MovieSession> getSessionById(int id) {
        String query = "SELECT * FROM sessions WHERE id = ?";
        MovieSession session = null;
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    session = new MovieSession(
                            rs.getInt("id"),
                            rs.getString("session_name")
                    );
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(session);
    }

    /**
     * updateSession
     * @param session MovieSession.
     */
    public void updateSession(MovieSession session) {
        String query = "UPDATE sessions SET session_name = ? WHERE id = ?";
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
