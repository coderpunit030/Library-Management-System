package com.example.library.service;

import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(String fullName, String email, String rawPassword) {
        String hash = encoder.encode(rawPassword);
        User user = new User(fullName, email.toLowerCase().trim(), hash);
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String rawPassword) {
        return userRepository.findByEmail(email.toLowerCase().trim())
                .filter(u -> encoder.matches(rawPassword, u.getPasswordHash()));
    }

    public boolean isActive(User user) {
        // Active if user has paid OR within 3-day trial
        if (user.isPaid()) return true;
        Duration sinceSignup = Duration.between(user.getCreatedAt(), LocalDateTime.now());
        return sinceSignup.toDays() < 3;
    }

    public long daysLeftInTrial(User user) {
        long days = 3 - Duration.between(user.getCreatedAt(), LocalDateTime.now()).toDays();
        return Math.max(days, 0);
    }

    public User markPaid(User user) {
        user.setPaid(true);
        user.setPaidAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
