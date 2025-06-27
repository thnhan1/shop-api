package com.nhanab.shop.dto.order;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateOrderRequest {
    private UUID orderId;
    private ShippingAddress shippingAddress;
    private OrderBuyer orderBuyer;
}
