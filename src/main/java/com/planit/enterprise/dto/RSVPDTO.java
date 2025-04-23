package com.planit.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RSVPDTO {
    private int id;
    private int userId;
    private int eventId;
    private int rsvpStatus;

    public RSVPDTO(int id, int userId, int eventId, String status) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.setRsvpStatus(status);
    }

    public RSVPDTO() {

    }

    public void setRsvpStatus(String status) {
        switch (status.toLowerCase()) {
            case "yes":
                this.rsvpStatus = 1;  // Yes
                break;
            case "no":
                this.rsvpStatus = 0;  // No
                break;
            case "maybe":
                this.rsvpStatus = 2;  // Maybe
                break;
            default:
                this.rsvpStatus = -1;  // Invalid status
                break;
        }
    }

    // Get status as a String for display purposes
    public String getRsvpStatusAsString() {
        return switch (this.rsvpStatus) {
            case 1 -> "Yes";
            case 0 -> "No";
            case 2 -> "Maybe";
            default -> "Pending";
        };
    }

}



