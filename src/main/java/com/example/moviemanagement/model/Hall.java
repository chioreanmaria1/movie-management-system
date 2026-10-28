package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

import java.util.ArrayList;
import java.util.List;

public class Hall implements HasId {
    private String id;
    private String name;
    private String theatreId;
    private int capacity;
    private List<Seat> seats;
    private List<Screening> screenings;

    public Hall(String id, String name, String theatreId, int capacity, List<Seat> seats, List<Screening> screenings) {
        this.id = id;
        this.name = name;
        this.theatreId = theatreId;
        this.capacity = capacity;
        this.seats = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public void addScreening(Screening screening){
        screenings.add(screening);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }
}
