package com.planit.enterprise.service.stubs;

import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserServiceStub implements IUserService {
    @Override
    public UserDTO fetchUserByID(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFName("Debbie");
        return userDTO;
    }

    @Override
    public UserDTO fetchUserByEmail(String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFName("Debbie");
        return userDTO;
    }

    @Override
    public Boolean doesEmailExist(String email) {
        boolean exists = false;
        if (Objects.equals(email, "debbie@email.com"))
        {
            exists = true;
        }
        return exists;
    }

    @Override
    public int registerUser(String fName, String lName, String email) {
        return 1;
    }
}
