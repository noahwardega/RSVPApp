package com.planit.enterprise.dto;

import java.util.List;

public class EventDTO {
    private int id;
    private String name;
    private String date;
    private String location;
    private List<RSVPDTO> attendees;

    // Constructor with parameters
    public EventDTO(int id, String name, String date, String location) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<RSVPDTO> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<RSVPDTO> attendees) {
        this.attendees = attendees;
    }
}

