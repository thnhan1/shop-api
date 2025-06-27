package com.nhanab.shop.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class OrderRequest implements Serializable {
    private UUID userId;
    private ShippingAddress shippingAddress;
    private OrderBuyer buyerInfo;
}
