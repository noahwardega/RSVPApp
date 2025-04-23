package com.planit.enterprise;

import com.planit.enterprise.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String redirectToSignIn() {
        return "redirect:/signIn";
    }

    @GetMapping("/signIn")
    public String showSignInForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signIn";
    }

    @PostMapping("/signIn")
    public String processEmailSignIn(@ModelAttribute UserDTO userDTO, Model model) {
        Optional<User> existingUser = userService.getUserByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            session.setAttribute("currentUser", existingUser.get());
            return "redirect:/start";
        } else {
            model.addAttribute("userDTO", userDTO);
            return "redirect:/completeAccount";
        }
    }

    @GetMapping("/completeAccount")
    public String showCompleteAccountForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "completeAccount";
    }

    @PostMapping("/completeAccount")
    public String completeAccountCreation(@ModelAttribute UserDTO userDTO) {
        try {
            userService.registerUser(userDTO.getFName(), userDTO.getLName(), userDTO.getEmail());

            Optional<User> newUser = userService.getUserByEmail(userDTO.getEmail());
            newUser.ifPresent(user -> session.setAttribute("currentUser", user));
        } catch (IllegalArgumentException e) {
            return "redirect:/signIn";
        }

        return "redirect:/start";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<UserDTO> searchUsers(@RequestParam("q") String query) {
        List<UserDTO> users = userService.searchUsersByEmail(query);
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        return users;
    }

    @GetMapping("/users/all")
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

}
