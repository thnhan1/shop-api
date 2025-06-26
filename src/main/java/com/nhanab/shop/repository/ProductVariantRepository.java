package com.nhanab.shop.repository;

import com.nhanab.shop.dto.cart.AddToCartDataDto;
import com.nhanab.shop.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    @Query("SELECT pv FROM ProductVariant pv " + "JOIN FETCH pv.product p " + "LEFT JOIN FETCH p.images " + "WHERE pv.id = :id")
    Optional<ProductVariant> findByIdWithProductAndImages(@Param("id") UUID id);

    @Query("""
    SELECT new com.nhanab.shop.dto.cart.AddToCartDataDto(
        pv.id, p.name, pv.price,
        COALESCE(
            (SELECT pi.url FROM ProductImage pi 
             WHERE pi.productVariant.id = pv.id AND pi.isThumbnail = true),
            (SELECT pi.url FROM ProductImage pi 
             WHERE pi.product.id = p.id AND pi.isThumbnail = true)
        ),
        c.id
    )
    FROM ProductVariant pv
    JOIN pv.product p
    JOIN Cart c ON c.user.id = :userId
    WHERE pv.id = :productVariantId
    """)
    Optional<AddToCartDataDto> findAddToCartData(
            @Param("productVariantId") UUID productVariantId,
            @Param("userId") UUID userId
    );
}
