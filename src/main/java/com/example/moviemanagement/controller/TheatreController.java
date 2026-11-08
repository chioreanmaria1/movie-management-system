package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Theatre;
import org.springframework.ui.Model;
import com.example.moviemanagement.service.CinemaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/theatres")
public class TheatreController {
    private final CinemaService service;

    public TheatreController(CinemaService service) {
        this.service = service;
    }

    @GetMapping
    public String findAllTheatres(Model model){
        model.addAttribute("theatres", service.findAllTheatres());
        return "theatre/index";
    }

    @GetMapping("/new")
    public String createTheatre(Model model){
        model.addAttribute("theatre", new Theatre());
        return "theatre/form";
    }

    @PostMapping
    public String save(@ModelAttribute Theatre theatre){
        service.saveTheatre(theatre);
        return "redirect:/theatres";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteTheatre(id);
        return "redirect:/theatres";
    }
}