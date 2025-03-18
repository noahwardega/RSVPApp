package com.planit.enterprise.service.stubs;

import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class UserServiceStub implements IUserService {
    
    // Create a map of team members for testing
    private final Map<String, UserDTO> teamMembers = new HashMap<>();
    
    public UserServiceStub() {
        // Initialize team members
        createTeamMember(1, "Emma", "Danner", "dannerec@mail.uc.edu");
        createTeamMember(2, "Dominic", "Scott", "scott2d5@mail.uc.edu");
        createTeamMember(3, "Noah", "Wardega", "wardegnh@mail.uc.edu");
        createTeamMember(4, "Calvin", "Yeboah", "yeboahcn@mail.uc.edu");
        
        // Keep the original test user
        createTeamMember(5, "Debbie", "Smith", "debbie@email.com");
    }
    
    private void createTeamMember(int id, String firstName, String lastName, String email) {
        UserDTO user = new UserDTO();
        user.setId(id);
        user.setFName(firstName);
        user.setLName(lastName);
        user.setEmail(email);
        teamMembers.put(email, user);
    }
    
    @Override
    public UserDTO fetchUserByID(int id) {
        // For simplicity, return the first team member
        return teamMembers.values().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserDTO fetchUserByEmail(String email) {
        System.out.println("Searching for user with email: " + email);
        System.out.println("Available emails: " + teamMembers.keySet());
        
        UserDTO user = teamMembers.get(email);
        System.out.println("User found: " + (user != null));
        
        return user;
    }

    @Override
    public Boolean doesEmailExist(String email) {
        return teamMembers.containsKey(email);
    }

    @Override
    public int registerUser(String fname, String lname, String email) {
        // For testing purposes, just return a new ID
        int newId = teamMembers.size() + 1;
        createTeamMember(newId, fname, lname, email);
        return newId;
    }
}
