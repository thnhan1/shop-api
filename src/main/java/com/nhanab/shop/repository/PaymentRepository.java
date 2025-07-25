package com.nhanab.shop.repository;

import com.nhanab.shop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}