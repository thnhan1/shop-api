package com.nhanab.shop.service;

import com.nhanab.shop.dto.order.OrderDto;
import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.dto.order.UpdateOrderRequest;
import com.nhanab.shop.model.PaymentMethod;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto placeOrder(OrderRequest orderRequest, PaymentMethod paymentMethod);

    OrderDto getOrderById(UUID orderId);

    List<OrderDto> getOrdersForUser(UUID userId);

    OrderDto updateOrder(UpdateOrderRequest updateOrderRequest);

    void cancelOrder(UUID orderId);
}
