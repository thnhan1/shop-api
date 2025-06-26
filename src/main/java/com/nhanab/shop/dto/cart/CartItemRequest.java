package com.nhanab.shop.dto.cart;

import lombok.Data;

import java.util.UUID;
@Data
public class CartItemRequest {
    private UUID userId;
    private UUID productVariantId;
    private Integer quantity;
}
