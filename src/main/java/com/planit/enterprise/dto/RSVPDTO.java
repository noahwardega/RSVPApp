package com.planit.enterprise.dto;

public class RSVPDTO {
    private int id;
    private int userId;
    private int eventId;
    private String rsvpStatus;

    // Constructor with parameters
    public RSVPDTO(int id, int userId, int eventId, String rsvpStatus) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.rsvpStatus = rsvpStatus;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getRsvpStatus() {
        return rsvpStatus;
    }

    public void setRsvpStatus(String rsvpStatus) {
        this.rsvpStatus = rsvpStatus;
    }
}


