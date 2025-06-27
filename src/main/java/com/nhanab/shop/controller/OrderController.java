package com.nhanab.shop.controller;

import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.model.Order;
import com.nhanab.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersForUser(@PathVariable UUID userId) {
        return orderService.getOrdersForUser(userId);
    }
}
