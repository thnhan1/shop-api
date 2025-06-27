package com.nhanab.shop.controller;

import com.nhanab.shop.dto.cart.CartDto;
import com.nhanab.shop.dto.cart.CartItemDto;
import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.model.Cart;
import com.nhanab.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Cart management APIs")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Get cart by user ID", description = "Retrieve the cart details for a specific user by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{userId}")
    public CartDto getCart(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable UUID userId) {
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

    @Operation(summary = "Add item to cart", description = "Add a product item to the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/items")
    public String addItemToCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cart item request", required = true)
            @RequestBody CartItemRequest cartItemRequest) {
        cartService.addItemToCart(cartItemRequest);
        return "success";
    }

    @Operation(summary = "Remove item from cart", description = "Remove a specific item from the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found in cart")
    })
    @DeleteMapping("/{userId}/items/{itemId}")
    public void removeItem(
            @Parameter(description = "User ID (UUID)", required = true) @PathVariable UUID userId,
            @Parameter(description = "Cart item ID (UUID)", required = true) @PathVariable UUID itemId) {
        cartService.removeItemFromCart(userId, itemId);
    }

    @Operation(summary = "Clear cart", description = "Remove all items from the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{userId}")
    public void clearCart(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable UUID userId) {
        cartService.clearCart(userId);
    }

    // unImplementYet
    @Operation(summary = "Merge guest cart with user cart", description = "Merge guest cart with authenticated user cart (not implemented yet)")
    @ApiResponse(responseCode = "200", description = "Cart merged successfully")
    @PostMapping("/merge/{guestCartId}")
    public void mergeCart(
            @Parameter(description = "Guest cart ID (UUID)", required = true)
            @PathVariable UUID guestCartId) {
        cartService.mergeGuestCartWithUserCart(guestCartId);
    }

}