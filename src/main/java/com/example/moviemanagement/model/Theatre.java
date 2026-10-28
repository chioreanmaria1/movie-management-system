package com.example.moviemanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private String city;
    private String phoneNumber; //new attribute
    private List<Hall> halls;

    public Theatre(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.halls = new ArrayList<>();
    }

    public void addHall(Hall hall){
        halls.add(hall);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Hall> getHalls() {
        return halls;
    }
}