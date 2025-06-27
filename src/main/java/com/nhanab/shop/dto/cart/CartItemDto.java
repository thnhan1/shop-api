package com.nhanab.shop.dto.cart;

import com.nhanab.shop.model.Cart;
import com.nhanab.shop.model.ProductVariant;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CartItemDto {
    private UUID id;

    private String productName;
    private String imageUrl;

    private int quantity;

    private BigDecimal price;

    private Boolean isActive = true;

    private UUID productVariantId;
}
