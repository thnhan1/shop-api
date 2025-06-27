package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.checkout.PayOSResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PayOSClient {
    public PayOSResponse createPayment(UUID orderId) {
        String base = "https://payos.example.com/" + orderId;
        return new PayOSResponse(base + "/checkout", base + "/payment");
    }
}
