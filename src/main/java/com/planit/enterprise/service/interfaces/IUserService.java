package com.planit.enterprise.service.interfaces;

import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDTO getUserByLName(String lName);
    User getUserById(int id);
    Optional<User> getUserByEmail(String email);
    List<UserDTO> getAllUsers();

    int registerUser(String test, String test1, String email);

    boolean existsByEmail(String email);

    User getCurrentUser(HttpSession session);
    List<UserDTO> searchUsersByEmail(String email);


}
