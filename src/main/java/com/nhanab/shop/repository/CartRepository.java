package com.nhanab.shop.repository;

import com.nhanab.shop.model.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = {
            "items",
            "items.productVariant",
            "items.productVariant.product",
            "items.productVariant.product.images"})
    Optional<Cart> findByUserId(UUID userId);

    @EntityGraph(attributePaths = {
            "items",
            "items.productVariant",
            "items.productVariant.product",
            "items.productVariant.product.images"})
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.id = :id")
    Optional<Cart> findByIdWithItems(UUID id);

}
