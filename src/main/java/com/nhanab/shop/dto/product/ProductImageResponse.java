package com.nhanab.shop.dto.product;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class ProductImageResponse {
    private UUID id;
    private String url;
    private Boolean isThumbnail;
    private Integer sortOrder;
}
