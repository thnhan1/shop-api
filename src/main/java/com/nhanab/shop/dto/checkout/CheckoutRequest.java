package com.nhanab.shop.dto.checkout;

import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.dto.order.ShippingAddress;
import com.nhanab.shop.dto.order.OrderBuyer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {

    private UUID userId;
    private Set<CartItemRequest> cartItems;
    private OrderBuyer buyerInfo;
    private ShippingAddress shippingAddress;

    private String paymentMethod;

}
