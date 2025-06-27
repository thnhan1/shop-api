package com.nhanab.shop.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID orderId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String trackingNumber;
    private LocalDateTime estimateDeliveryDate;
    private String phoneNumber;
    private String customerName;

    private ShippingAddress shippingAddress;
    private Set<OrderItemDto> items;
}
