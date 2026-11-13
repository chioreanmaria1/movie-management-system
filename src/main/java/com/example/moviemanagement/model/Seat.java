package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

public class Seat implements HasId {
    private String id;
    private String hallId;
    private String row;
    private String column;
    private boolean isAvailable;

    public Seat(String id, String hallId, String row, String column, boolean isAvailable) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
    }

    public Seat(){
        this.isAvailable = true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setColumn(String column) {
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

    public String getColumn() {
        return column;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}