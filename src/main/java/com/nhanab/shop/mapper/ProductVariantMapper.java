package com.nhanab.shop.mapper;

import com.nhanab.shop.dto.product.update.UpdateProductVariantRequest;
import com.nhanab.shop.model.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    void updateFromDto(UpdateProductVariantRequest dto, @MappingTarget ProductVariant productVariant);
}
