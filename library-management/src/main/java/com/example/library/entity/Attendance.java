package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(optional = false)
    private User user;

    public Attendance() {}

    public Attendance(User user, LocalDate date) {
        this.user = user;
        this.date = date;
    }

    // getters & setters
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
