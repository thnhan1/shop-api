package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.exception.ResourceNotFoundException;
import com.nhanab.shop.model.*;
import com.nhanab.shop.repository.CartRepository;
import com.nhanab.shop.repository.OrderRepository;
import com.nhanab.shop.repository.PaymentRepository;
import com.nhanab.shop.repository.UserRepository;
import com.nhanab.shop.service.CartService;
import com.nhanab.shop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByIdWithItems(orderRequest.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductVariant(cartItem.getProductVariant());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setImageUrl(cartItem.getImageUrl());
            order.getItems().add(orderItem);
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod("COD");
        payment.setPaymentStatus("PENDING");
        payment.setPaidAt(LocalDateTime.now());
        payment.setPaymentAmount(calculateTotal(cart));
        order.setPayment(payment);

        order = orderRepository.save(order);

        cartService.clearCart(user.getId());

        return order;
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
