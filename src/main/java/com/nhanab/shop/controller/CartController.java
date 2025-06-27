package com.nhanab.shop.controller;

import com.nhanab.shop.dto.cart.CartDto;
import com.nhanab.shop.dto.cart.CartItemDto;
import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.model.Cart;
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

    public CartDto getCart(@PathVariable UUID userId) {
        Cart cart =  cartService.getCartByUserId(userId);
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        Set<CartItemDto> cartItemDtos = new HashSet<>();
        for (var c: cart.getItems()) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(c.getId());
            cartItemDto.setQuantity(c.getQuantity());
            cartItemDto.setIsActive(c.getIsActive());
            cartItemDto.setPrice(c.getPrice());
            cartItemDto.setImageUrl(c.getImageUrl());
            cartItemDto.setProductName(c.getProductName());
            cartItemDto.setProductVariantId(c.getProductVariant().getId());
            cartItemDtos.add(cartItemDto);
        }
        cartDto.setItems(cartItemDtos);
        return  cartDto;


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
