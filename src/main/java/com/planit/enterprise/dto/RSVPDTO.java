package com.planit.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RSVPDTO {
    // Getters and setters
    private int id;
    private int userId;
    private int eventId;
    private String status;

    public RSVPDTO(int id, int id1, int id2, String status) {
        this.id = id;
        this.userId = id1;
        this.eventId = id2;
        this.status = status;
    }


}


