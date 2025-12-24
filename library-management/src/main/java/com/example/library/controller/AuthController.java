package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam @Email String email,
            @RequestParam @NotBlank String password,
            HttpSession session,
            Model model
    ) {
        return userService.login(email, password)
                .map(user -> {
                    session.setAttribute("userId", user.getId());
                    return "redirect:/profile";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Invalid email or password");
                    return "login";
                });
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam @NotBlank String fullName,
            @RequestParam @Email String email,
            @RequestParam @NotBlank String password,
            HttpSession session,
            Model model
    ) {
        if (userService.login(email, password).isPresent()) {
            model.addAttribute("error", "Account already exists. Please login.");
            return "login";
        }
        try {
            User user = userService.signup(fullName, email, password);
            session.setAttribute("userId", user.getId());
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("error", "Signup failed. Email may already be in use.");
            return "signup";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
