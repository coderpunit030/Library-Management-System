package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        boolean active = userService.isActive(user);
        long trialDaysLeft = userService.daysLeftInTrial(user);

        model.addAttribute("user", user);
        model.addAttribute("active", active);
        model.addAttribute("trialDaysLeft", trialDaysLeft);
        return "profile";
    }
}
