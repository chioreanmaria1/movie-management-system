package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Hall;
import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.service.BookingService;
import com.example.moviemanagement.service.CinemaService;
import com.example.moviemanagement.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {
    private final CinemaService service;
    private final BookingService bookingService;
    private final StaffService staffService;

    public ScreeningController(CinemaService service, BookingService bookingService, StaffService staffService) {
        this.service = service;
        this.bookingService = bookingService;
        this.staffService = staffService;
    }

    @GetMapping
    public String findAllScreenings(Model model){
        model.addAttribute("screenings", service.findAllScreenings());
        return "screening/index";
    }

    @GetMapping("/new")
    public String createScreening(Model model){
        model.addAttribute("screening", new Screening());
        return "screening/form";
    }

    @PostMapping
    public String save(@ModelAttribute Screening screening){
        service.saveScreening(screening);
        return "redirect:/screenings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteScreening(id);
        return "redirect:/screenings";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model){
        Screening screening  = service.findScreeningById(id);
        model.addAttribute("screening", screening);
        return "screening/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateScreenings(@PathVariable String id, @ModelAttribute Screening screening){
        Screening originalScreening = service.findScreeningById(id);
        screening.setId(id);
        screening.setHallId(originalScreening.getHallId());
        screening.setMovieId(originalScreening.getMovieId());
        service.saveScreening(screening);
        return "redirect:/screenings";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model){
        Screening screening  = service.findScreeningById(id);
        model.addAttribute("screening", screening);
        model.addAttribute("tickets", bookingService.getTicketsByScreening(id));
        model.addAttribute("assignments", staffService.findStaffForScreening(id));
        return "screening/details";
    }
}