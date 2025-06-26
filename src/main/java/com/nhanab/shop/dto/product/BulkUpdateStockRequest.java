package com.nhanab.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
