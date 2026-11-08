package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Seat;
import com.example.moviemanagement.service.CinemaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/seats")
public class SeatController {
    private final CinemaService service;

    public SeatController(CinemaService service) {
        this.service = service;
    }

    @GetMapping
    public String findAllSeats(Model model){
        model.addAttribute("seats", service.findAllSeats());
        return "seat/index";
    }

    @GetMapping("/new")
    public String createSeat(Model model){
        model.addAttribute("seat", new Seat());
        return "seat/form";
    }

    @PostMapping
    public String save(@ModelAttribute Seat seat){
        service.saveSeat(seat);
        return "redirect:/seats";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteSeat(id);
        return "redirect:/seats";
    }
}