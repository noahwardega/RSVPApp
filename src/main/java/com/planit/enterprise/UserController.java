package com.planit.enterprise;

import org.springframework.ui.Model;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

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
        } catch (IllegalArgumentException e) {
            return "redirect:/signIn";
        }

        return "redirect:/start";
    }
}