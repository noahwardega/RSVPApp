package com.planit.enterprise.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private List<RSVPDTO> rsvpEvents;
}
