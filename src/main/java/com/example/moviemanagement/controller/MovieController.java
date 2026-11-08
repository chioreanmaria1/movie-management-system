package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Genre;
import com.example.moviemanagement.model.Movie;
import com.example.moviemanagement.service.CinemaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final CinemaService service;

    public MovieController(CinemaService service) {
        this.service = service;
    }

    @GetMapping
    public String findAllMovies(Model model){
        model.addAttribute("movies", service.findAllMovies());
        return "movie/index";
    }

    @GetMapping("/new")
    public String createMovie(Model model){
        model.addAttribute("movie", new Movie());
        model.addAttribute("genres", Genre.values());
        return "movie/form";
    }

    @PostMapping
    public String save(@ModelAttribute Movie movie){
        service.saveMovie(movie);
        return "redirect:/movies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteMovie(id);
        return "redirect:/movies";
    }
}
