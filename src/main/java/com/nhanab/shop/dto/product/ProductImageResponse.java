package com.nhanab.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponse {
    private UUID id;
    private String url;
    private Boolean isThumbnail;
    private Integer sortOrder;
}
