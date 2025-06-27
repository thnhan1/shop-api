package com.nhanab.shop.dto.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderBuyer implements Serializable {
    private String name;
    private String phoneNumber;
}
