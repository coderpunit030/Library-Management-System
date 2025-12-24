package com.example.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String passwordHash;

    private LocalDateTime createdAt;

    private boolean paid;

    private LocalDateTime paidAt;

    public User() {}

    public User(String fullName, String email, String passwordHash) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.paid = false;
    }

    // Getters & setters

    public Long getId() { return id; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isPaid() { return paid; }

    public void setPaid(boolean paid) { this.paid = paid; }

    public LocalDateTime getPaidAt() { return paidAt; }

    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}
