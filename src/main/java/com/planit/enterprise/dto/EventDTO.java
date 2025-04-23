package com.planit.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class EventDTO {
    private int id;
    private String name;
    private LocalDateTime date;
    private String location;
    private int hostId;
    private List<Integer> attendeeIds;

    //CODE REVIEW: I added in Stevens suggestion of deleting redundant getters and setters because lombok already made them -Noah Wardega


    public EventDTO(int id, String name, String location, LocalDateTime date, int id1, List<Integer> attendeeIds) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.hostId = id1;
        this.attendeeIds = attendeeIds;
    }

    public EventDTO() {

    }

    public EventDTO(int id, String name, String location, LocalDateTime date, int id1) {
    }

}

