package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

public class Ticket implements HasId {
    private String id;
    private String screeningId;
    private String customerId;
    private String seatId;
    private double price;

    public Ticket(String id, String screeningId, String customerId, String seatId, double price) {
        this.id = id;
        this.screeningId = screeningId;
        this.customerId = customerId;
        this.seatId = seatId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
