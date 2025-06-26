package com.nhanab.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Entity
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;

    private String sku;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductImage productImage;

    @OneToMany(mappedBy = "productVariant")
    private Set<CartItem> catItems = new HashSet<>();

}
