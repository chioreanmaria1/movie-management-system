package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.*;
import com.example.moviemanagement.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {
    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/theatres")
    public List<Theatre> findAllTheatres(){
        return cinemaService.findAllTheatres();
    }

    @GetMapping("/halls")
    public List<Hall> findAllHalls(){
        return cinemaService.findAllHalls();
    }

    @GetMapping("/seats")
    public List<Seat> findAllSeats(){
        return cinemaService.findAllSeats();
    }

    @GetMapping("/screenings")
    public List<Screening> findAllScreenings(){
        return cinemaService.findAllScreenings();
    }

    @GetMapping("/movies")
    public List<Movie> findAllMovies(){
        return cinemaService.findAllMovies();
    }

    @GetMapping("/theatres/{id}")
    public Theatre findTheatreById(@PathVariable String id){
        return cinemaService.findTheatreById(id);
    }

    @GetMapping("/halls/{id}")
    public Hall findHallById(@PathVariable String id){
        return cinemaService.findHallById(id);
    }

    @GetMapping("/seats/{id}")
    public Seat findSeatById(@PathVariable String id){
        return cinemaService.findSeatById(id);
    }

    @GetMapping("/screenings/{id}")
    public Screening findScreeningById(@PathVariable String id){
        return cinemaService.findScreeningById(id);
    }

    @GetMapping("/movies/{id}")
    public Movie findMovieById(@PathVariable String id){
        return cinemaService.findMovieById(id);
    }

    @PostMapping
    public void saveTheatre(@RequestBody Theatre theatre){
        cinemaService.saveTheatre(theatre);
    }

    @PostMapping
    public void saveHall(@RequestBody Hall hall){
        cinemaService.saveHall(hall);
    }

    @PostMapping
    public void saveSeat(@RequestBody Seat seat){
        cinemaService.saveSeat(seat);
    }

    @PostMapping
    public void saveScreening(@RequestBody Screening screening){
        cinemaService.saveScreening(screening);
    }

    @PostMapping
    public void saveMovie(@RequestBody Movie movie){
        cinemaService.saveMovie(movie);
    }
}