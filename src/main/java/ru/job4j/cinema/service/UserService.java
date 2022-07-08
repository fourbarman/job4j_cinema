package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDBStore;

import java.util.List;
import java.util.Optional;

/**
 * UserService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 07.07.2022.
 */
@ThreadSafe
@Service
public class UserService {
    private final UserDBStore users;

    public UserService(UserDBStore users) {
        this.users = users;
    }

    public List<User> getAllUsers() {
        return this.users.getAll();
    }

    public Optional<User> addUser(User user) {
        return this.users.addUser(user);
    }

    public Optional<User> findUserBiId(int id) {
        return this.users.findUserById(id);
    }

    public void updateUser(User user) {
        this.users.updateUser(user);
    }
}
