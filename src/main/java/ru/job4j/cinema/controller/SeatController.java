package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.SeatService;

@ThreadSafe
@Controller
public class SeatController {
    private final SeatService seats;

    public SeatController(SeatService seats) {
        this.seats = seats;
    }
    @GetMapping("/seats")
    public String seats(Model model) {
        model.addAttribute(this.seats.gelAll());
        return "seats";
    }
}
