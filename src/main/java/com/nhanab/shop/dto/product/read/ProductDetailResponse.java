package com.nhanab.shop.dto.product.read;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.product.ProductImageResponse;
import com.nhanab.shop.dto.product.ProductVariantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponse {
    private UUID id;
    private String name;
    private String description;
    private CategoryResponse category;
    private List<ProductVariantResponse> variants;
    private List<ProductImageResponse> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
