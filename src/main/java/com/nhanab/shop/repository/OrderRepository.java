package com.nhanab.shop.repository;

import com.nhanab.shop.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @EntityGraph(attributePaths = {
            "items",
            "items.productVariant",
            "items.productVariant.product"
    })
    @Query("select o from Order o where o.id = :id")
    Optional<Order> findWithItemsById(@Param("id") UUID id);

    @EntityGraph(attributePaths = {
            "items",
            "items.productVariant",
            "items.productVariant.product"
    })
    List<Order> findByUserId(UUID userId);
}