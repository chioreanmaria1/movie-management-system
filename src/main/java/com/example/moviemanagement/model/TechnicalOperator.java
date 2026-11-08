package com.example.moviemanagement.model;

public class TechnicalOperator extends Staff{
    private Specialization specialization;

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public TechnicalOperator() {
    }

    public TechnicalOperator(String id, String name, Specialization specialization) {
        super(id, name);
        this.specialization = specialization;

    }
}