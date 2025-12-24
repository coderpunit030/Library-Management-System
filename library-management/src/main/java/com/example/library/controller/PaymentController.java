package com.example.library.controller;

import com.example.library.entity.Payment;
import com.example.library.entity.User;
import com.example.library.repository.PaymentRepository;
import com.example.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final UserService userService;

    public PaymentController(PaymentRepository paymentRepository, UserService userService) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
    }

    @GetMapping("/payment")
    public String paymentPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("amount", new BigDecimal("199.00")); // Example fee
        return "payment";
    }

    @PostMapping("/payment/submit")
    public String submitPayment(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        // Simulate successful payment
        BigDecimal amount = new BigDecimal("199.00");
        String ref = "PAY-" + UUID.randomUUID();

        Payment payment = new Payment(user, amount, ref);
        paymentRepository.save(payment);

        userService.markPaid(user);

        model.addAttribute("message", "Payment successful! Reference: " + ref);
        return "redirect:/profile";
    }
}
