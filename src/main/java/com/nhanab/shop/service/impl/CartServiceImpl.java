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
        return cartRepository.findByUserIdWithItems(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
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

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductVariantId(cart.getId(), productVariant.getId());
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemRequest.getQuantity());
            item.setUpdatedAt(LocalDateTime.now());
            return;
        }

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
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for user");
        }
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(UUID userId) {
        cartRepository.findByUserIdWithItems(userId).ifPresent(cart -> {
            cart.getItems().clear();
            cartRepository.save(cart);
        });
    }

    @Override
    public void mergeGuestCartWithUserCart(UUID guestCartId) {
        Cart guestCart = cartRepository.findById(guestCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest cart not found"));
        Cart userCart = getCartByUserId(guestCart.getUser().getId());

        for (CartItem guestItem : guestCart.getItems()) {
            Optional<CartItem> existing = cartItemRepository
                    .findByCartIdAndProductVariantId(userCart.getId(), guestItem.getProductVariant().getId());
            if (existing.isPresent()) {
                CartItem item = existing.get();
                item.setQuantity(item.getQuantity() + guestItem.getQuantity());
            } else {
                CartItem newItem = new CartItem();
                newItem.setCart(userCart);
                newItem.setProductVariant(guestItem.getProductVariant());
                newItem.setQuantity(guestItem.getQuantity());
                newItem.setPrice(guestItem.getPrice());
                newItem.setProductName(guestItem.getProductName());
                newItem.setImageUrl(guestItem.getImageUrl());
                cartItemRepository.save(newItem);
                userCart.getItems().add(newItem);
            }
        }

        cartRepository.delete(guestCart);
    }
}


