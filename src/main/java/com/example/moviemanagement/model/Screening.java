package com.example.moviemanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Screening {
    private String id;
    private String hallId;
    private String movieId;
    private LocalDateTime date;
    private List<Ticket> tickets;
    private List<StaffAssignment> assignments;

    public Screening(String id, String hallId, String movieId, LocalDateTime date, List<Ticket> tickets, List<StaffAssignment> assignments) {
        this.id = id;
        this.hallId = hallId;
        this.movieId = movieId;
        this.date = date;
        this.tickets = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public void addAssignment(StaffAssignment assignment){
        assignments.add(assignment);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<StaffAssignment> getAssignments() {
        return assignments;
    }
}