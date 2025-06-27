package com.nhanab.shop.controller;

import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}")
    public Object getCart(@PathVariable UUID userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/items")
    public String addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        cartService.addItemToCart(cartItemRequest);
        return "success";
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public void removeItem(@PathVariable UUID userId, @PathVariable UUID itemId) {
        cartService.removeItemFromCart(userId, itemId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable UUID userId) {
        cartService.clearCart(userId);
    }

    @PostMapping("/merge/{guestCartId}")
    public void mergeCart(@PathVariable UUID guestCartId) {
        cartService.mergeGuestCartWithUserCart(guestCartId);
    }
}
