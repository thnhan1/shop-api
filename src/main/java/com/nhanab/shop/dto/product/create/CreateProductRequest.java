package com.nhanab.shop.dto.product.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/*
* Create Product request body
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "CreateProductRequest",
        description = "Request DTO for creating new product with its variants and images"
)
public class CreateProductRequest implements Serializable {

    @Schema(
            description = "Name of product",
            example = "T-Shirt anime style",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Detailed description of the product",
            example = "T-shirt 100% cotton, suitable for all aged, ...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @Schema(
            description = "Category Id of this product",
            example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID categoryId;

    @Schema(
            description = "Variant of the product",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<CreateVariantDto> variants;

    @Schema(
            description = "Images of product",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<CreateProductImageRequest> images;

}