package com.planit.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private int id;
    private String fName;
    private String lName;
    private String email;

    public UserDTO(int id, String fName, String lName, String email) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public UserDTO(int id, Object fName, Object lName, String email) {
    }

    public UserDTO() {

    }

}
