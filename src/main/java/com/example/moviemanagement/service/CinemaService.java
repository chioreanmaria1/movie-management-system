package com.example.moviemanagement.service;

import com.example.moviemanagement.model.*;
import com.example.moviemanagement.repository.*;
import com.example.moviemanagement.repository.memory.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


@Service
public class CinemaService {
    private final BaseRepository<Theatre> theatreRepository;
    private final BaseRepository<Hall> hallRepository;
    private final BaseRepository<Seat> seatRepository;
    private final BaseRepository<Movie> movieRepository;
    private final BaseRepository<Screening> screeningRepository;
    private final BaseRepository<StaffAssignment> staffAssignmentRepository;
    private final BaseRepository<Ticket> ticketRepository;

    public static final List<String> seatRows = new ArrayList<>();  //listele apartin clasei (static)
    public static final List<String> seatColumns = new ArrayList<>();

    public CinemaService(BaseRepository<Theatre> theatreRepository, BaseRepository<Hall> hallRepository, BaseRepository<Seat> seatRepository, BaseRepository<Movie> movieRepository, BaseRepository<Screening> screeningRepository, BaseRepository<StaffAssignment> staffAssignmentRepository, BaseRepository<Ticket> ticketRepository) {
        this.theatreRepository = theatreRepository;
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.ticketRepository = ticketRepository;
    }

    //we create the list of rows and columns
    static{
        for(char character = 'A'; character<='T'; character++){
            seatRows.add(String.valueOf(character));
        }
        for(int i = 1; i<=20; i++){
            seatColumns.add(String.valueOf(i));
        }
    }

    public static int maxHallCapacity(){  //the maximum capacity of any hall
        return seatRows.size()*seatColumns.size();
    }

    public List<String> getSeatRows(){
        return seatRows;
    }

    public List<String> getSeatColumns(){
        return seatColumns;
    }

    private boolean datesOverlap(Screening screening1, Screening screening2){
        Movie movie1 = movieRepository.findById(screening1.getMovieId());
        Movie movie2 = movieRepository.findById(screening2.getMovieId());
        if (movie1 == null || movie2 == null){
            throw new IllegalArgumentException("Movie doesn't exist!");
        }
        LocalDateTime startScreening1 = screening1.getDate();
        LocalDateTime endScreening1 = startScreening1.plusMinutes(movie1.getDurationMin());
        LocalDateTime startScreening2 = screening2.getDate();
        LocalDateTime endScreening2 = startScreening2.plusMinutes(movie2.getDurationMin());

        return startScreening1.isBefore(endScreening2) && startScreening2.isBefore(endScreening1);
    }

    public void saveTheatre(Theatre theatre){
        if (theatre.getName().trim().isEmpty() || theatre.getName() == null){
            throw new IllegalArgumentException("Theatre must have a name!");
        }
        if (theatre.getCity().trim().isEmpty() || theatre.getCity() == null){
            throw new IllegalArgumentException("There must have a city!");
        }
        theatreRepository.save(theatre);
    }

    public void saveHall(Hall hall){
        if(theatreRepository.findById(hall.getTheatreId())==null || !(theatreRepository.findById(hall.getTheatreId()).isAvailable())){
            throw new IllegalArgumentException("Hall cannot be added because the theatre with id "+hall.getTheatreId()+" does not exist!");
        }
        if(hall.getName().isEmpty() || hall.getName()==null){
            throw new IllegalArgumentException("Hall must have a name!");
        }
        int hallCapacity = hall.getCapacity();
        if (hallCapacity<= 0 || hallCapacity > maxHallCapacity()){  //we need to see if the capacity of the hall we want to add is > than the max capacity
            throw new IllegalArgumentException("Hall can't have a capacity <= 0 or greater than the maximum one!");
        }
        hallRepository.save(hall);
    }

    public void saveSeat(Seat seat){
        Hall hall = hallRepository.findById(seat.getHallId());
        if(hall == null || !hall.isAvailable()){
            throw new IllegalArgumentException("Seat cannot be added because the hall with id "+seat.getHallId()+" does not exist!");
        }

        if(seat.getRow() == null || !seatRows.contains(seat.getRow().trim().toUpperCase())){
            throw new IllegalArgumentException("Row of the seat must be a letter between A and T!");
        }
        if(seat.getColumn() == null || !seatColumns.contains(seat.getColumn().trim())){
            throw new IllegalArgumentException("Column of the seat must be a number between 1 and 20");
        }
        seat.setRow(seat.getRow().trim().toUpperCase());
        seat.setColumn(seat.getColumn().trim());

        List<Seat> seats = findSeatsByHallId(seat.getHallId());   //seeing if the seat we want to add already exists (the row and column)
        for(Seat otherSeat: seats){
            if(!otherSeat.getId().equals(seat.getId()) && otherSeat.getRow().equals(seat.getRow())&& otherSeat.getColumn().equals(seat.getColumn())){
                throw new IllegalArgumentException("The seat with row and column "+seat.getRow()+" "+seat.getColumn()+" already exists in the hall!");
            }
        }

        boolean isNew = false;
        if(seat.getId() == null || seat.getId().isEmpty()){  //isNew tell us if the seat we want to add is new(we want to add it) or not (we want to edit it, so it already has an id)
            isNew = true;
        }

        if(isNew && seats.size() >=hall.getCapacity()){
            throw new IllegalArgumentException("Hall is already full!");
        }
        seatRepository.save(seat);
    }

    public void saveMovie(Movie movie){
        if (movie.getTitle().trim().isEmpty() || movie.getTitle() == null){
            throw new IllegalArgumentException("Movie must have a name!");
        }
        if (movie.getDurationMin() <= 0){
            throw new IllegalArgumentException("Duration can't be negative or zero!");
        }

        movieRepository.save(movie);
    }

    public void saveScreening(Screening screening){
        Hall screeningHall = hallRepository.findById(screening.getHallId());
        if(screeningHall == null ||  screeningHall.isAvailable() == false) {
            throw new IllegalArgumentException("Screening cannot be added because the hall with id "+ screening.getHallId()+" does not exist or is unavailable!");
        }
        if(movieRepository.findById(screening.getMovieId()) ==null) {
            throw new IllegalArgumentException("Screening cannot be added because the movie with id "+ screening.getMovieId()+" does not exist!");
        }

        for(Screening object: findScreeningsByHallId(screening.getHallId())){
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
        List<Hall> halls = new ArrayList<>();
        for(Hall hall: hallRepository.findAll()){
            if(hall.getTheatreId().equals(id)){
                halls.add(hall);
            }
        }
        return halls;
    }

    public List<Screening> findScreeningsByHallId(String id){
        List<Screening> screenings = new ArrayList<>();
        for(Screening screening: screeningRepository.findAll()){
            if(screening.getHallId().equals(id)){
                screenings.add(screening);
            }
        }
        return screenings;
    }

    public List<Screening> findScreeningsByMovieId(String id){
        List<Screening> screenings = new ArrayList<>();
        for(Screening screening: screeningRepository.findAll()){
            if(screening.getMovieId().equals(id)){
                screenings.add(screening);
            }
        }
        return screenings;
    }

    public List<Seat> findSeatsByHallId(String id){
        List<Seat> seats = new ArrayList<>();
        for(Seat seat: seatRepository.findAll()){
            if(seat.getHallId().equals(id)){
                seats.add(seat);
            }
        }
        return seats;
    }

    public void deleteScreening(String screeningId){
        if(screeningRepository.findById(screeningId) == null){
            throw new IllegalArgumentException("You can't delete a screening that doesn't exist!");
        }
        if (screeningHasTickets(screeningId)){
            throw new IllegalArgumentException("You can't delete this screening because it has tickets!");
        }
        List<String> assignmentIds = new ArrayList<>();
        for(StaffAssignment assignment : staffAssignmentRepository.findAll()){
            if(assignment.getScreeningId().equals(screeningId)){
                assignmentIds.add(assignment.getId());
            }
        }
        for(String assignmentId : assignmentIds) {
            staffAssignmentRepository.delete(assignmentId);
        }
        screeningRepository.delete(screeningId);
    }

    public boolean screeningHasTickets(String screeningId){
        for (Ticket ticket: ticketRepository.findAll()){
            if (ticket.getScreeningId().equals(screeningId)){
                return true;
            }
        }
        return false;
    }

    public void deleteMovie(String id){
        if(movieRepository.findById(id) == null){
            throw new IllegalArgumentException("You can't delete a movie that doesn't exist!");
        }
        if (!findScreeningsByMovieId(id).isEmpty()) {
            throw new IllegalArgumentException("Movie with id " + id + " cannot be deleted because it has screenings!");
        }
        movieRepository.delete(id);
    }

    public void deleteTheatre(String id){
        Theatre theatre = theatreRepository.findById(id);

        if(theatre == null){
            throw new IllegalArgumentException("Theatre with id "+id+" doesn't exist!");
        }

        List<Hall> halls = findHallsByTheatreId(id);
        for(Hall hall: halls) {
            List<Seat> seats = findSeatsByHallId(hall.getId());
            for (Seat seat : seats) {
                seatRepository.delete(seat.getId());
            }
            hallRepository.delete(hall.getId());
        }
        theatreRepository.delete(id);
    }

    public void deleteHall(String id){
        Hall hall = hallRepository.findById(id);
        if(hall == null){
            throw new IllegalArgumentException("Hall with id "+id+" doesn't exist!");
        }

        List<Seat> seats = findSeatsByHallId(hall.getId());
        for (Seat seat : seats) {
            seatRepository.delete(seat.getId());
        }
        hallRepository.delete(hall.getId());
    }

    public void deleteSeat(String id){
        Seat seat = seatRepository.findById(id);
        if(seat == null){
            throw new IllegalArgumentException("Seat with id "+id+" doesn't exist!");
        }
        seatRepository.delete(seat.getId());
    }

    public void setSeatAvailability(String seatId, boolean availability){
        Seat seat = seatRepository.findById(seatId);
        if(seat == null){
            throw new IllegalArgumentException("Seat with id "+seatId+" doesn't exist!");
        }
        seat.setAvailable(availability);
        seatRepository.save(seat);
    }

    public void setHallAvailability(String hallId, boolean availability){
        Hall hall = hallRepository.findById(hallId);
        if(hall == null){
            throw new IllegalArgumentException("Hall with id "+hallId+" doesn't exist!");
        }

        List<Seat> seats = findSeatsByHallId(hallId);
        for(Seat seat: seats){
            setSeatAvailability(seat.getId(), availability);
        }
        hall.setAvailable(availability);
        hallRepository.save(hall);
    }

    public void setTheatreAvailability(String theatreId, boolean availability){
        Theatre theatre = theatreRepository.findById(theatreId);

        if(theatre == null){
            throw new IllegalArgumentException("Theatre with id "+theatreId+" doesn't exist!");
        }

        List<Hall> halls = findHallsByTheatreId(theatreId);
        for(Hall hall: halls){
            setHallAvailability(hall.getId(),availability);
        }

        theatre.setAvailable(availability);
        theatreRepository.save(theatre);
    }

    public int numberOfHallsInTheatre(String theatreId){
        if(theatreRepository.findById(theatreId) == null){
            throw new IllegalArgumentException("Theatre with id "+theatreId+" does not exist!");
        }
        return findHallsByTheatreId(theatreId).size();
    }

    public int numberOfSeatsInHall(String hallId){
        if(hallRepository.findById(hallId) == null){
            throw new IllegalArgumentException("Hall with id "+hallId+" does not exist!");
        }
        return findSeatsByHallId(hallId).size();
    }

    public int numberOfScreeningsOfMovie(String movieId){
        if(movieRepository.findById(movieId) == null){
            throw new IllegalArgumentException("Movie with id "+movieId+" does not exist!");
        }
        return findScreeningsByMovieId(movieId).size();
    }

    public List<Movie> moviesByGenre(Genre genre){
        List<Movie> movies = new ArrayList<>();
        for(Movie movie: movieRepository.findAll()){
            if(movie.getGenre() == genre){
                movies.add(movie);
            }
        }
        return movies;
    }
}