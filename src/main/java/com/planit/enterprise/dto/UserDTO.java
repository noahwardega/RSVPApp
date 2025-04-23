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

    //CODE REVIEW: I added in Stevens suggestion of deleting redundant getters and setters because lombok already made them -Noah Wardega


    public UserDTO(int id, String fName, String lName, String email) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }



    public UserDTO() {

    }

}
