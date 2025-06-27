package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.checkout.CheckoutRequest;
import com.nhanab.shop.dto.checkout.PayOSResponse;
import com.nhanab.shop.dto.order.OrderDto;
import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.model.PaymentMethod;
import com.nhanab.shop.service.CheckoutService;
import com.nhanab.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final OrderService orderService;
    private final PayOSClient payOSClient;

    @Override
    public OrderDto checkoutWithCod(CheckoutRequest request) {
        OrderRequest orderRequest = toOrderRequest(request);
        return orderService.placeOrder(orderRequest, PaymentMethod.COD);
    }

    @Override
    public PayOSResponse checkoutWithPayos(CheckoutRequest request) {
        OrderRequest orderRequest = toOrderRequest(request);
        OrderDto order = orderService.placeOrder(orderRequest, PaymentMethod.PAYOS);
        return payOSClient.createPayment(order.getOrderId());
    }

    private OrderRequest toOrderRequest(CheckoutRequest request) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(request.getUserId());
        orderRequest.setShippingAddress(request.getShippingAddress());
        orderRequest.setBuyerInfo(request.getBuyerInfo());
        return orderRequest;
    }
}
