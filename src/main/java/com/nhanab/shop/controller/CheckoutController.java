package com.nhanab.shop.controller;

import com.nhanab.shop.dto.checkout.CheckoutRequest;
import com.nhanab.shop.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/checkout")
@RestController
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    @PostMapping
    public ResponseEntity<String> checkout(CheckoutRequest checkoutRequest) {
    }
}
