package com.example.moviemanagement.service;

import com.example.moviemanagement.model.*;
import com.example.moviemanagement.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;


@Service
public class CinemaService {
    private final TheatreRepository theatreRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final StaffAssignmentRepository staffAssignmentRepository;

    public CinemaService(TheatreRepository theatreRepository, HallRepository hallRepository, SeatRepository seatRepository, MovieRepository movieRepository, ScreeningRepository screeningRepository, StaffAssignmentRepository staffAssignmentRepository) {
        this.theatreRepository = theatreRepository;
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
    }

    private boolean datesOverlap(Screening screening1, Screening screening2){
        Movie movie1 = movieRepository.findById(screening1.getMovieId());
        Movie movie2 = movieRepository.findById(screening2.getMovieId());

        LocalDateTime startScreening1 = screening1.getDate();
        LocalDateTime endScreening1 = startScreening1.plusMinutes(movie1.getDurationMin());
        LocalDateTime startScreening2 = screening2.getDate();
        LocalDateTime endScreening2 = startScreening2.plusMinutes(movie2.getDurationMin());

        if(endScreening1.isBefore(startScreening2) || endScreening2.isBefore(startScreening1) ){
            return false;
        }
        return true;
    }

    public void saveTheatre(Theatre theatre){theatreRepository.save(theatre);}

    public void saveHall(Hall hall){
        if(theatreRepository.findById(hall.getTheatreId())==null){
            throw new IllegalArgumentException("Hall cannot be added because the theatre with id "+hall.getTheatreId()+" does not exist!");
        }
        hallRepository.save(hall);
    }

    public void saveSeat(Seat seat){
        if(hallRepository.findById(seat.getHallId())==null){
            throw new IllegalArgumentException("Seat cannot be added because the hall with id "+seat.getHallId()+" does not exist!");
        }
        seatRepository.save(seat);
    }

    public void saveMovie(Movie movie){
        movieRepository.save(movie);
    }

    public void saveScreening(Screening screening){
        if(hallRepository.findById(screening.getHallId()) == null) {
            throw new IllegalArgumentException("Screening cannot be added because the hall with id "+ screening.getHallId()+" does not exist!");
        }
        if(movieRepository.findById(screening.getMovieId()) ==null) {
            throw new IllegalArgumentException("Screening cannot be added because the movie with id "+ screening.getMovieId()+" does not exist!");
        }

        for(Screening object: screeningRepository.findByHallId(screening.getHallId())){
            if(!object.getId().equals(screening.getId()) && datesOverlap(object, screening)){
                throw new IllegalArgumentException("Screening cannot be saved because its date overlaps with the other screenings");
            }
        }

        screeningRepository.save(screening);
    }

    public List<Theatre> findAllTheatres(){
        return theatreRepository.findAll();
    }

    public List<Hall> findAllHalls(){
        return hallRepository.findAll();
    }

    public List<Seat> findAllSeats(){
        return seatRepository.findAll();
    }

    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public List<Screening> findAllScreenings(){
        return screeningRepository.findAll();
    }

    public Theatre findTheatreById (String id){
        return theatreRepository.findById(id);
    }

    public Hall findHallById(String id){
        return hallRepository.findById(id);
    }

    public Seat findSeatById(String id){
        return seatRepository.findById(id);
    }

    public Movie findMovieById(String id){
        return movieRepository.findById(id);
    }

    public Screening findScreeningById(String id){
        return screeningRepository.findById(id);
    }

    public List<Hall> findHallsByTheatreId(String id){
        return hallRepository.findByTheatreId(id);
    }

    public List<Screening> findScreeningsByHallId(String id){
        return screeningRepository.findByHallId(id);
    }

    public List<Screening> findScreeningsByMovieId(String id){
        return screeningRepository.findByMovieId(id);
    }

    public List<Seat> findSeatsByHallId(String id){
        return seatRepository.findByHallId(id);
    }

    public void deleteScreening(String id){
        if(screeningRepository.findById(id) == null){
            throw new IllegalArgumentException("You can't delete a screening that doesn't exist!");
        }
        staffAssignmentRepository.delete(id);
        screeningRepository.delete(id);
    }

    public void deleteMovie(String id){
        if(movieRepository.findById(id) == null){
            throw new IllegalArgumentException("You can't delete a movie that doesn't exist!");
        }
        if (!screeningRepository.findByMovieId(id).isEmpty()) {
            throw new IllegalArgumentException("Movie with id " + id + " cannot be deleted because it has screenings!");
        }
        movieRepository.delete(id);
    }

    public int numberOfHallsInTheatre(String theatreId){
        if(theatreRepository.findById(theatreId) == null){
            throw new IllegalArgumentException("Theatre with id "+theatreId+" does not exist!");
        }
        return hallRepository.findByTheatreId(theatreId).size();
    }

    public int numberOfSeatsInHall(String hallId){
        if(hallRepository.findById(hallId) == null){
            throw new IllegalArgumentException("Hall with id "+hallId+" does not exist!");
        }
        return seatRepository.findByHallId(hallId).size();
    }

    public int numberOfScreeningsOfMovie(String movieId){
        if(movieRepository.findById(movieId) == null){
            throw new IllegalArgumentException("Movie with id "+movieId+" does not exist!");
        }
        return screeningRepository.findByMovieId(movieId).size();
    }
}