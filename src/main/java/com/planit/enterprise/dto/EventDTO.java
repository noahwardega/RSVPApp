package com.planit.enterprise.dto;

import lombok.Data;
import java.util.List;

@Data
public class EventDTO {
    private int id;
    private String name;
    private String date;
    private String location;
    private List<RSVPDTO> attendees;
}

