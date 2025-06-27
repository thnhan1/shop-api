package com.nhanab.shop.controller;

import com.nhanab.shop.dto.checkout.CheckoutRequest;
import com.nhanab.shop.dto.checkout.PayOSResponse;
import com.nhanab.shop.dto.order.OrderDto;
import com.nhanab.shop.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/checkout")
@RestController
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping("/cod")
    public OrderDto checkoutCod(@RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.checkoutWithCod(checkoutRequest);
    }

    @PostMapping("/payos")
    public PayOSResponse checkoutPayOS(@RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.checkoutWithPayos(checkoutRequest);
    }
}
