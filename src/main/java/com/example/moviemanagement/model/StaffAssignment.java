package com.example.moviemanagement.model;

public class StaffAssignment {
    private String id;
    private String screeningId;
    private String staffId;

    public StaffAssignment(String id, String screeningId, String staffId) {
        this.id = id;
        this.screeningId = screeningId;
        this.staffId = staffId;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
