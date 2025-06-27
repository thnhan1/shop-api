package com.nhanab.shop.dto.order;

import jakarta.persistence.Embeddable;
import lombok.Data;
@Embeddable
@Data
public class ShippingAddress {
    private String street;
    private String address;
    private String city;
}
