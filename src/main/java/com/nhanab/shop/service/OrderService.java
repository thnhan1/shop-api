package com.nhanab.shop.service;

import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.model.Order;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);
}
