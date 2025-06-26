package com.nhanab.shop.dto.product.create;

import lombok.*;

import java.util.List;
import java.util.UUID;

/*
* Create Product request body
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String name;
    private String description;

    private UUID categoryId;

    private List<CreateVariantDto> variants;
    private List<CreateProductImageRequest> images;

}