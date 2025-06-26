package com.nhanab.shop.repository;

import com.nhanab.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCartIdAndProductVariantId(UUID cartId, UUID productVariantId);
}
