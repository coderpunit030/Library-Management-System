package com.example.library.repository;

import com.example.library.entity.Attendance;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);
    boolean existsByUserAndDate(User user, LocalDate date);
}

