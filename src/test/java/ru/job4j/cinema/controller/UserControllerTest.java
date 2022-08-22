package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * UserControllerTest test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.08.2022.
 */
public class UserControllerTest {
    /**
     * Test when users then return all users.
     */
    @Test
    public void whenUsers() {
        List<User> users = Arrays.asList(
                new User(1, "userName1", "userName1@email.com", "1"),
                new User(1, "userName2", "userName2@email.com", "2")
        );
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(userService.getAllUsers()).thenReturn(users);
        UserController userController = new UserController(userService);
        String page = userController.users(model, httpSession);
        verify(model).addAttribute("users", users);
        assertThat(page).isEqualTo("users");
    }

    /**
     * Test when login with valid user than success and return index page.
     */
    @Test
    public void whenLogin() {
        UserService userService = mock(UserService.class);
        User user = new User(1, "userName2", "userName2@email.com", "2");
        when(userService.findUserByEmailAndPhone("userName2@email.com", "2")).thenReturn(Optional.of(user));
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(httpServletRequest.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/index");
    }

    /**
     * Test when login with invalid user than fail and return to loginPage.
     */
    @Test
    public void whenLoginGuest() {
        UserService userService = mock(UserService.class);
        User user = new User(1, "userName2", "userName2@email.com", "2");
        when(userService.findUserByEmailAndPhone("userName2@email.com", "2")).thenReturn(Optional.empty());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(httpServletRequest.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/loginPage?fail=true");
    }

    /**
     * Test when register with valid data than success and return to index page.
     */
    @Test
    public void whenRegisterSuccess() {
        UserService userService = mock(UserService.class);
        User user = new User(1, "userName2", "userName2@email.com", "2");
        when(userService.addUser(user)).thenReturn(Optional.of(user));
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(httpServletRequest.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.register(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/index");
    }

    /**
     * Test register when invalid data than fail and return to register.
     */
    @Test
    public void whenRegisterFail() {
        UserService userService = mock(UserService.class);
        User user = new User(1, "userName2", "userName2@email.com", "2");
        when(userService.addUser(user)).thenReturn(Optional.empty());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession session = new MockHttpSession();
        when(httpServletRequest.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.register(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/register?fail=true");
    }

    /**
     * Test registerForm when return to register page.
     */
    @Test
    public void whenRegisterForm() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        boolean fail = true;
        String page = userController.registerForm(model, fail);
        assertThat(page).isEqualTo("register");
    }

    /**
     * Test logout when logout than return to loginPage.
     */
    @Test
    public void whenLogout() {
        UserService userService = mock(UserService.class);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", "user");
        UserController userController = new UserController(userService);
        String page = userController.logout(httpSession);
        assertThat(page).isEqualTo("redirect:/loginPage");
    }
}
