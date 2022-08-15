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

    /**
     * getAllUsers.
     *
     * @return all users.
     */
    public List<User> getAllUsers() {
        return this.users.getAll();
    }

    /**
     * addUser.
     *
     * @param user User.
     * @return added User.
     */
    public Optional<User> addUser(User user) {
        return this.users.addUser(user);
    }

    /**
     * findUserByEmailAndPhone.
     *
     * @param email String Email.
     * @param phone String Phone.
     * @return found User by email and phone.
     */
    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return this.users.findUserByEmailAndPhone(email, phone);
    }

    /**
     * findUserById.
     *
     * @param id User id.
     * @return found User.
     */
    public Optional<User> findUserById(int id) {
        return this.users.findUserById(id);
    }
}
