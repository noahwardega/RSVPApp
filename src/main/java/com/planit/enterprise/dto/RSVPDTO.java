package com.planit.enterprise.dto;

import lombok.Data;

@Data
public class RSVPDTO {
    private int eventId;
    private int userId;
    private String rsvpStatus;

    public RSVPDTO(int eventId, int userId, String rsvpStatus)
    {
        this.eventId = eventId;
        this.userId = userId;
        this.rsvpStatus = rsvpStatus;
    }
}

