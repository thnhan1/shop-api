package com.nhanab.shop.mapper;

import com.nhanab.shop.dto.product.*;
import com.nhanab.shop.dto.product.create.CreateProductImageRequest;
import com.nhanab.shop.dto.product.create.CreateProductRequest;
import com.nhanab.shop.dto.product.create.CreateVariantDto;
import com.nhanab.shop.dto.product.read.ProductDetailResponse;
import com.nhanab.shop.dto.product.read.ProductSummaryResponse;
import com.nhanab.shop.model.Product;
import com.nhanab.shop.model.ProductImage;
import com.nhanab.shop.model.ProductVariant;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mapping(target = "variants", source = "variants")
    @Mapping(target="category", source = "category")
    ProductDetailResponse toDetailResponse(Product product);

    @Mapping(target = "minPrice", ignore = true)
    @Mapping(target = "maxPrice", ignore = true)
    @Mapping(target = "totalStock", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    @Mapping(target = "variantCount", ignore = true)
    ProductSummaryResponse toSummaryResponse(Product product);

    // Mapping ProductVariant
    @Mapping(target = "imageUrl", source = "productImage.url")
    ProductVariantResponse toVariantResponse(ProductVariant variant);

    List<ProductVariantResponse> toVariantResponseList(List<ProductVariant> variants);

    // Mapping ProductImage
    ProductImageResponse toImageResponse(ProductImage image);

    List<ProductImageResponse> toImageResponseList(List<ProductImage> images);

    // Mapping từ Request DTO sang Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
//    @Mapping(target = "imageUrl", ignore = true)
    ProductVariant toVariantEntity(CreateVariantDto request);

    @Mapping(target = "id", ignore = true)
    ProductImage toImageEntity(CreateProductImageRequest request);

    // Custom mapping methods cho các trường calculated
    @AfterMapping
    default void calculateSummaryFields(@MappingTarget ProductSummaryResponse response, Product product) {
        if (product.getVariants() != null && !product.getVariants().isEmpty()) {
            // Calculate min/max price
            BigDecimal minPrice = product.getVariants().stream()
                    .map(ProductVariant::getPrice)
                    .filter(Objects::nonNull)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            BigDecimal maxPrice = product.getVariants().stream()
                    .map(ProductVariant::getPrice)
                    .filter(Objects::nonNull)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            // Calculate total stock
            Integer totalStock = product.getVariants().stream()
                    .map(ProductVariant::getStock)
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            // Get thumbnail URL
            String thumbnailUrl = product.getImages().stream()
                    .filter(ProductImage::getIsThumbnail) // First, try to find the actual thumbnail
                    .findFirst() // Get the first one if it exists
                    .map(ProductImage::getUrl) // Map it to its URL
                    .orElseGet(() -> { // If no thumbnail found, then...
                        // ...get the URL of the very first image in the original list
                        return product.getImages().stream()
                                .findFirst() // Get the first image
                                .map(ProductImage::getUrl) // Map it to its URL
                                .orElse(null); // Return null if there are no images at all
                    });

            response.setMinPrice(minPrice);
            response.setMaxPrice(maxPrice);
            response.setTotalStock(totalStock);
            response.setThumbnailUrl(thumbnailUrl);
            response.setVariantCount(product.getVariants().size());
        }
    }
}
