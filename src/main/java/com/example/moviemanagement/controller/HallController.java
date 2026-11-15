package com.example.moviemanagement.controller;


import com.example.moviemanagement.model.Hall;
import com.example.moviemanagement.service.CinemaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/halls")
public class HallController {
    private final CinemaService service;

    public HallController(CinemaService service) {
        this.service = service;
    }

    @GetMapping
    public String findAllHalls(Model model){
        model.addAttribute("halls", service.findAllHalls());
        return "hall/index";
    }

    @GetMapping("/new")
    public String createHall(Model model){
        model.addAttribute("hall", new Hall());
        return "hall/form";
    }

    @PostMapping
    public String save(@ModelAttribute Hall hall){
        service.saveHall(hall);
        return "redirect:/halls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteHall(id);
        return "redirect:/halls";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model){
        Hall hall = service.findHallById(id);
        model.addAttribute("hall", hall);
        return "hall/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateHall(@PathVariable String id, @ModelAttribute Hall hall){
        Hall originalHall = service.findHallById(id);
        hall.setId(id);
        hall.setTheatreId(originalHall.getTheatreId());
        service.saveHall(hall);
        return "redirect:/halls";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model){
        Hall hall = service.findHallById(id);
        model.addAttribute("hall", hall);
        model.addAttribute("seats", service.findSeatsByHallId(id));
        model.addAttribute("screenings", service.findScreeningsByHallId(id));
        return "hall/details";
    }
}