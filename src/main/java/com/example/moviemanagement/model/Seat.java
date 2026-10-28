package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

public class Seat implements HasId {
    private String id;
    private String hallId;
    private String row;
    private String collumn;

    public void setId(String id) {
        this.id = id;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setCollumn(String collumn) {
        this.collumn = collumn;
    }

    public String getId() {
        return id;
    }

    public String getHallId() {
        return hallId;
    }

    public String getRow() {
        return row;
    }

    public String getCollumn() {
        return collumn;
    }

    public Seat(String id, String hallId, String row, String collumn) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.collumn = collumn;
    }
}

