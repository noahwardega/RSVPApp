package com.planit.enterprise.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private List<RSVPDTO> rsvpEvents;
}
