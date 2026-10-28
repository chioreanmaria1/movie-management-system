package com.example.moviemanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String genre; //new attribute
    private int durationMin;
    private List<Screening> screenings;

    public Movie(String id, String title, int durationMin, List<Screening> screenings) {
        this.id = id;
        this.title = title;
        this.durationMin = durationMin;
        this.screenings = new ArrayList<>();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }
}
