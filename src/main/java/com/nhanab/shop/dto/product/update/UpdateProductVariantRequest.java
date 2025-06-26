package com.nhanab.shop.dto.product.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductVariantRequest {
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;
    private String sku;
    private String imageUrl;
}