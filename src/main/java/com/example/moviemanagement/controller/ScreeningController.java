package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.service.CinemaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {
    private final CinemaService service;

    public ScreeningController(CinemaService service) {
        this.service = service;
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
}