package com.nhanab.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {
    private String name;
    private UUID categoryId;
    private String color;
    private String productSize;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean inStock;
    private String sortBy = "name"; // name, price, createdAt
    private String sortDirection = "ASC"; // ASC, DESC
    private Integer page = 0;
    private Integer size = 20;
}
