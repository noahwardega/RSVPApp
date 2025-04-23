package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.repository.UserRepository;
import com.planit.enterprise.service.interfaces.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a User by their ID.
     *
     * @param id the ID of the user
     * @return the User object
     * @throws RuntimeException if the user is not found
     */
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Retrieves a User by their email.
     *
     * @param email the user's email
     * @return an Optional containing the User if found
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves all users in the system as DTOs.
     *
     * @return a list of UserDTOs
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getFName(), user.getLName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Registers a new user with the provided details.
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email
     * @return the ID of the newly registered user
     * @throws IllegalArgumentException if the email already exists
     */
    @Override
    public int registerUser(String firstName, String lastName, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFName(firstName);
        user.setLName(lastName);
        user.setEmail(email);

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    /**
     * Checks if a user exists by email.
     *
     * @param email the email to check
     * @return true if a user with the email exists, false otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Retrieves the currently logged-in user from the session.
     *
     * @param session the current HTTP session
     * @return the current User object or null if not set
     */
    @Override
    public User getCurrentUser(HttpSession session) {
        Object userObj = session.getAttribute("currentUser");
        if (userObj instanceof User) {
            return (User) userObj;
        }
        return null;
    }

    /**
     * Searches for users whose email contains the provided string, case-insensitive.
     *
     * @param email the partial email to search for
     * @return a list of UserDTOs matching the search
     */
    @Override
    public List<UserDTO> searchUsersByEmail(String email) {
        List<User> users = userRepository.findByEmailContainingIgnoreCase(email);
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets the full name of a user by their ID.
     *
     * @param id the user's ID
     * @return the full name of the user, or "Unknown User" if not found
     */
    @Override
    public String getFullNameById(int id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return user.getFName() + " " + user.getLName();
        } else {
            return "Unknown User";
        }
    }

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user the User entity
     * @return the corresponding UserDTO
     */
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getFName(), user.getLName(), user.getEmail());
    }
}



