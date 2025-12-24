package com.example.library.repository;

import com.example.library.entity.Payment;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);
}
