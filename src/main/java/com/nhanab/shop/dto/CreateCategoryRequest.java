package com.nhanab.shop.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateCategoryRequest implements Serializable {
    private String name;
    private String description;
}
