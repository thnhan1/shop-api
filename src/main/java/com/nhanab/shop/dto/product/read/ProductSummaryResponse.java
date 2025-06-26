package com.nhanab.shop.dto.product.read;

import com.nhanab.shop.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSummaryResponse {
    private UUID id;
    private String name;
    private String description;
    private CategoryResponse category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer totalStock;
    private String thumbnailUrl;
    private Integer variantCount;
}
