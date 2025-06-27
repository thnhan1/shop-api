package com.nhanab.shop.repository;

import com.nhanab.shop.model.Product;
import com.nhanab.shop.model.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @EntityGraph(
            attributePaths = {
                    "variants",
                    "variants.productImage",
                    "images",
                    "category"
            }
    )
   @Query(value = "select p from Product p where p.id = :id")
    Optional<Product> findById(@Param("id") UUID id);

    Page<Product> findAllByCategoryId(UUID categoryId, Pageable pageable);
}
