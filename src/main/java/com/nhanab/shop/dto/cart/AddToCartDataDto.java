package com.nhanab.shop.dto.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AddToCartDataDto {
    private UUID productVariantId;
    private String productName;
    private BigDecimal price;
    private String imageUrl;
    private UUID cartId;

}
