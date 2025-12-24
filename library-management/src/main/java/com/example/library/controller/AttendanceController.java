package com.example.library.controller;

import com.example.library.entity.Attendance;
import com.example.library.entity.User;
import com.example.library.repository.AttendanceRepository;
import com.example.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;

    public AttendanceController(AttendanceRepository attendanceRepository, UserService userService) {
        this.attendanceRepository = attendanceRepository;
        this.userService = userService;
    }

    @PostMapping("/attendance/mark")
    public String markAttendance(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        LocalDate today = LocalDate.now();
        if (!attendanceRepository.existsByUserAndDate(user, today)) {
            attendanceRepository.save(new Attendance(user, today));
        }

        return "redirect:/profile";
    }

    @GetMapping("/attendance")
    public String viewAttendance(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        List<Attendance> records = attendanceRepository.findByUser(user);
        model.addAttribute("attendanceRecords", records);
        return "attendance";
    }
}
