package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDBStore {
    private BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * getAll.
     *
     * @return List.
     */
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        List<User> users = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone")
                    ));
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
        String query = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.executeQuery();
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
        String query = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    /**
     * updateUser.
     *
     * @param user User.
     */
    public void updateUser(User user) {
        String query = "UPDATE users SET username = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
