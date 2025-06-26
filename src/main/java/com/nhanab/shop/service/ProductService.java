package com.nhanab.shop.service;

import com.nhanab.shop.dto.product.update.UpdateProductVariantRequest;
import com.nhanab.shop.dto.product.create.CreateProductRequest;
import com.nhanab.shop.dto.payload.PagedResponse;
import com.nhanab.shop.dto.product.read.ProductDetailResponse;
import com.nhanab.shop.dto.product.read.ProductSummaryResponse;

import java.util.UUID;

public interface ProductService {
    ProductDetailResponse create(CreateProductRequest request);

    void delete(UUID id);

     ProductDetailResponse getById(UUID id);

    PagedResponse<ProductSummaryResponse> getSummarizedProducts(int page, int size);

    void updateProductVariant(UUID variantId, UpdateProductVariantRequest updateProductVariantDto);
}
