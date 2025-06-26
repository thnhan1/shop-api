package com.nhanab.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private int quantity;

    @Column(precision = 38, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant productVariant;
}
