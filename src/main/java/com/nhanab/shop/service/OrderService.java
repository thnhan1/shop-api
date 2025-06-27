package com.nhanab.shop.service;

import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);

    Order getOrderById(UUID orderId);

    List<Order> getOrdersForUser(UUID userId);
}
