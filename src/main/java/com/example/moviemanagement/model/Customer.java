package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class Customer implements HasId {
    private String id;
    private String name;
    private int birthYear;
    private List<Ticket> ticketList;

    public Customer(String id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.ticketList = new ArrayList<>();
    }

    public Customer() {
        ticketList = new ArrayList<>();
    }

    public void addTicket(Ticket ticket){
        this.ticketList.add(ticket);
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

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public boolean canWatch(Movie movie) {
        int currentYear = LocalDate.now().getYear();
        if (movie.isAgeRestricted() && (currentYear - this.birthYear < 18)) {
            return false;
        }
        return true;
    }
}