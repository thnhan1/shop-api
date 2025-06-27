package com.nhanab.shop.dto.product;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class ProductVariantResponse {
    private UUID id;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;
    private String sku;
    private String imageUrl;
}
