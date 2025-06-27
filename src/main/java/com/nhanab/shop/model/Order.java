package com.nhanab.shop.model;

import com.nhanab.shop.dto.order.ShippingAddress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderDate;
    private String orderStatus;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;

    private String trackingNumber;
    private LocalDateTime estimateDeliveryDate;

    private String note;

    @Embedded
    private ShippingAddress shippingAddress;

    // private String customerInfo
    private String phoneNumber;
    private String customerName;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

}