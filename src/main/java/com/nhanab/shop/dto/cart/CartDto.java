package com.nhanab.shop.dto.cart;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class CartDto {
    private UUID cartId;
    private Set<CartItemDto> items = new HashSet<>();
}
