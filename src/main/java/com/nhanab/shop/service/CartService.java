package com.nhanab.shop.service;

import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.model.Cart;

import java.util.UUID;

public interface CartService {
    Cart getCartByUserId(UUID userId);

    void addItemToCart(CartItemRequest cartItemRequest);

    void removeItemFromCart(UUID userId, UUID cartItemId);

    void clearCart(UUID userId);

    void mergeGuestCartWithUserCart(UUID string);


}
