package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;
import java.time.Period;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class Customer implements HasId {
    private String id;
    private String name;
    private LocalDate birthDate;
    private List<Ticket> ticketList;

    public Customer(String id, String name, LocalDate birthYear) {
        this.id = id;
        this.name = name;
        this.birthDate = birthYear;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean canWatch(Movie movie) {
        Period period = Period.between(this.birthDate, LocalDate.now());
        if (movie.isAgeRestricted() && period.getYears() < 18) {
            return false;
        }
        return true;
    }
}