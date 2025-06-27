package com.nhanab.shop.repository;

import com.nhanab.shop.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    @Query("SELECT pv FROM ProductVariant pv " + "JOIN FETCH pv.product p " + "LEFT JOIN FETCH p.images " + "WHERE pv.id = :id")
    Optional<ProductVariant> findByIdWithProductAndImages(@Param("id") UUID id);

}
