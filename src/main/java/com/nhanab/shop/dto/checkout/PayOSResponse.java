package com.nhanab.shop.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayOSResponse {
    private String checkoutUrl;
    private String paymentUrl;
}
