package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

import java.util.ArrayList;
import java.util.List;

public class Customer implements HasId {
    private String id;
    private String name;
    private boolean isAdult; //new attribute
    private List<Ticket> ticketList;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.ticketList = new ArrayList<>();
    }

    public void addTickets(Ticket ticket){
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
}