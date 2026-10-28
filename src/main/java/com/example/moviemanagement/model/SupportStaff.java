package com.example.moviemanagement.model;

public class SupportStaff extends Staff{
    private String role;

    public SupportStaff(String id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
