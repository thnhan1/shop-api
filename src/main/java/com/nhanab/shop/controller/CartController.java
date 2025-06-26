package com.nhanab.shop.controller;

import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public String addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        cartService.addItemToCart(cartItemRequest);
        return "success";
    }
}
