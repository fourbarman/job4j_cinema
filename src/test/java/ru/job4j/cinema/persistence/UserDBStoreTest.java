package ru.job4j.cinema.persistence;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
/**
 * UserDBStoreTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.08.2022.
 */

public class UserDBStoreTest {
    /**
     * Test getAll().
     */
    @Test
    public void whenGetAll() {
        UserDBStore userDBStore = new UserDBStore(new Main().loadPool());
        Optional<User> user1 = userDBStore.addUser(new User(1, "username1", "email1", "phone1"));
        Optional<User> user2 = userDBStore.addUser(new User(1, "username2", "email2", "phone2"));
        assertThat(user1).isPresent();
        assertThat(user2).isPresent();
        assertThat(userDBStore.getAll())
                .contains(user1.get(), user2.get());
    }

    /**
     * Test addUser().
     */
    @Test
    public void whenAddUser() {
        UserDBStore userDBStore = new UserDBStore(new Main().loadPool());
        User user = new User(1, "username3", "email3", "phone3");
        Optional<User> addedUser = userDBStore.addUser(user);
        assertThat(addedUser).isPresent();
        assertThat(addedUser.get().getUsername())
                .isEqualTo(user.getUsername());
    }

    /**
     * Test findUserByUd(int id).
     */
    @Test
    public void whenFindUserById() {
        UserDBStore userDBStore = new UserDBStore(new Main().loadPool());
        User user = userDBStore.addUser(new User(1, "username4", "email4", "phone4")).get();
        Optional<User> foundUser = userDBStore.findUserById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get())
                .isEqualTo(user);
    }

    /**
     * Test findUserByEmailAndPhone(String email, String phone).
     */

    @Test
    public void whenFindUserByEmailAndPhone() {
        UserDBStore userDBStore = new UserDBStore(new Main().loadPool());
        User user = userDBStore.addUser(new User(1, "username5", "email5", "phone5")).get();
        Optional<User> foundUser = userDBStore.findUserByEmailAndPhone(user.getEmail(), user.getPhone());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }
}
