package com.example.library.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    @ManyToOne(optional = false)
    private User user;

    private String referenceId;

    public Payment() {}

    public Payment(User user, BigDecimal amount, String referenceId) {
        this.user = user;
        this.amount = amount;
        this.referenceId = referenceId;
        this.timestamp = LocalDateTime.now();
    }

    // Getters & setters

    public Long getId() { return id; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getReferenceId() { return referenceId; }

    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
}
