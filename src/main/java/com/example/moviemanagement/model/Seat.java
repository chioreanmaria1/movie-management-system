package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

public class Seat implements HasId {
    private String id;
    private String hallId;
    private String row;
    private String column;

    public void setId(String id) {
        this.id = id;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setCollumn(String column) {
        this.column = column;
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
        return column;
    }

    public Seat(String id, String hallId, String row, String column) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.column = column;
    }
}

