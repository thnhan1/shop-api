package com.nhanab.shop.dto.product;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Hidden
@Schema(hidden = true)
public class BulkUpdateStockRequest {
    private List<VariantStockUpdate> updates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VariantStockUpdate {
        private UUID variantId;

        private Integer stock;
    }
}
