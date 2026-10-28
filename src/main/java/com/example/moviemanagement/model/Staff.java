package com.example.moviemanagement.model;

import com.example.moviemanagement.repository.HasId;

public abstract class Staff implements HasId {
    public String id;
    public String name;

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
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
}
