package com.nhanab.shop.dto.product.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductImageRequest {
    private String url;
    private Boolean isThumbnail = false;
    private Integer sortOrder = 0;
}
