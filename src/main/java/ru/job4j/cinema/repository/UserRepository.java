package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
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
 * @since 06.07.2022.
 */
@Repository
public class UserRepository {
    private final BasicDataSource pool;
    private static final String SELECT_ALL = "SELECT * FROM users;";
    private static final String INSERT_USER = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String SELECT_USER_BY_EMAIL_AND_PHONE = "SELECT * FROM users WHERE email = ? AND phone = ?;";

    public UserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     *
     * @return List.
     */
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(getUserFromRS(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return users;
    }

    /**
     * addUser.
     *
     * @param user User.
     * @return Optional.
     */
    public Optional<User> addUser(User user) {
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    /**
     * findUserById.
     *
     * @param id id.
     * @return Optional.
     */
    public Optional<User> findUserById(int id) {
        User user = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = getUserFromRS(rs);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    /**
     * findUserByEmailAndPhone.
     *
     * @param email String email.
     * @param phone String phone.
     * @return Optional.
     */
    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        User user = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_USER_BY_EMAIL_AND_PHONE)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = getUserFromRS(rs);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Return User from ResultSet.
     *
     * @param resultSet ResultSet.
     * @return User.
     * @throws SQLException Exception.
     */
    private User getUserFromRS(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }
}
