package ru.job4j.cinema.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * UserSessionFilter.
 * Add attribute to request
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 14.09.2022.
 */
@Component
@Order(1)
public class UserSessionFilter implements Filter {
    /**
     * doFilter.
     * If "user" attribute is null than set it to "guest" or set it to user as if in request.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        req.setAttribute("user", user);
        chain.doFilter(req, res);
    }
}
