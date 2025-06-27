package com.nhanab.shop.service;

import com.nhanab.shop.dto.checkout.CheckoutRequest;
import com.nhanab.shop.dto.checkout.PayOSResponse;
import com.nhanab.shop.dto.order.OrderDto;

public interface CheckoutService {
    OrderDto checkoutWithCod(CheckoutRequest request);
    PayOSResponse checkoutWithPayos(CheckoutRequest request);
}
