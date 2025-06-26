package com.nhanab.shop.service.impl;
import com.nhanab.shop.dto.cart.AddToCartDataDto;
import com.nhanab.shop.dto.cart.CartItemRequest;
import com.nhanab.shop.exception.ResourceNotFoundException;
import com.nhanab.shop.model.*;
import com.nhanab.shop.repository.*;
import com.nhanab.shop.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCartByUserId(UUID userId) {
        return null;
    }

    @Transactional
    @Override
    public void addItemToCart(CartItemRequest cartItemRequest) {
        AddToCartDataDto data = productVariantRepository
                .findAddToCartData(cartItemRequest.getProductVariantId(), cartItemRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("not found product variant or cart"));
        // Query 2: Lấy Cart entity và ProductVariant entity (nếu cần relationship)
        Cart cart = cartRepository.findById(data.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        ProductVariant productVariant = productVariantRepository.findById(data.getProductVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductVariant not found"));

        // Tạo CartItem với relationship
        CartItem cartItem = new CartItem();
        cartItem.setProductVariant(productVariant); // Entity relationship
        cartItem.setCart(cart);                     // Entity relationship
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setPrice(data.getPrice());
        cartItem.setProductName(data.getProductName());
        cartItem.setImageUrl(data.getImageUrl());
        cartItem.setIsActive(true);
        cartItem.setCreatedAt(LocalDateTime.now());

        // Query 3: Save
        cartItemRepository.save(cartItem);
//        // Chỉ 1 query, fetch tất cả cần thiết
//        ProductVariant productVariant = productVariantRepository
//                .findByIdWithProductAndImages(cartItemRequest.getProductVariantId())
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "ProductVariant not found: " + cartItemRequest.getProductVariantId()));
//
//        // 1 query đơn giản
//        Cart cart = cartRepository.findByUserId(cartItemRequest.getUserId());
//
//        CartItem cartItem = new CartItem();
//        cartItem.setProductVariant(productVariant);
//        cartItem.setQuantity(cartItemRequest.getQuantity());
//        cartItem.setPrice(productVariant.getPrice());
//        cartItem.setProductName(productVariant.getProduct().getName());
//
//        // Data đã được fetch, không trigger thêm query
//        Optional<String> imageUrl = productVariant.getProduct().getImages().stream()
//                .filter(image -> image.getProductVariant() != null &&
//                        Objects.equals(image.getProductVariant().getId(),
//                                cartItemRequest.getProductVariantId()))
//                .map(ProductImage::getUrl)
//                .findFirst();
//
//        cartItem.setImageUrl(imageUrl.orElse(null));
//        cartItem.setIsActive(true);
//        cartItem.setCreatedAt(LocalDateTime.now());
//        cartItem.setCart(cart);
//
//        cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItemFromCart(UUID userId, UUID cartItemId) {

    }

    @Override
    public void clearCart(UUID userId) {

    }

    @Override
    public void mergeGuestCartWithUserCart(UUID string) {

    }
}


