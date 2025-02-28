package com.planit.enterprise.dto;

import lombok.Data;

@Data
public class RSVPDTO {
    private int eventId;
    private int userId;
    private String rsvpStatus;
}
