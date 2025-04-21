package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.repository.UserRepository;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserByLName(String lName) {
        User user = userRepository.findBylName(lName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getFName(), user.getLName(), user.getEmail());
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getFName(), user.getLName(), user.getEmail()))
                .collect(Collectors.toList());
    }

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

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}


