package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.order.OrderDto;
import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.dto.order.UpdateOrderRequest;
import com.nhanab.shop.exception.ResourceNotFoundException;
import com.nhanab.shop.mapper.OrderMapper;
import com.nhanab.shop.model.*;
import com.nhanab.shop.repository.*;
import com.nhanab.shop.service.CartService;
import com.nhanab.shop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByIdWithItems(user.getCart().getId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Init Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PENDING");
        order.setShippingAddress(request.getShippingAddress());
        order.setPhoneNumber(request.getBuyerInfo().getPhoneNumber());
        order.setCustomerName(request.getBuyerInfo().getName());
        order.setCreatedAt(LocalDateTime.now());

        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            ProductVariant variant = productVariantRepository.findById(cartItem.getProductVariant().getId()).orElseThrow(() -> new ResourceNotFoundException("Product variant not found"));

            if (variant.getStock() < cartItem.getQuantity()) {
                throw new IllegalStateException("Product " + variant.getSku() + " Out of stock");
            }

            // Trừ kho
            variant.setStock(variant.getStock() - cartItem.getQuantity());

            // Tạo OrderItem
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductVariant(variant);
            item.setProductName(variant.getProduct().getName());
            item.setImageUrl(variant.getProductImage().getUrl());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(variant.getPrice());
            item.setQuantity(cartItem.getQuantity());
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItems.add(item);

        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setTaxAmount(total.multiply(BigDecimal.valueOf(0.1)));

        // payment
        Payment payment = new Payment();
        payment.setPaymentAmount(order.getTotalAmount().add(order.getTaxAmount()));
        payment.setOrder(order);
        payment.setPaymentMethod("BANKING");
        payment.setPaymentStatus("PENDING");

        order.setPayment(payment);
        order = orderRepository.save(order);

        // clear cart
        cart.getItems().clear();
        cartRepository.save(cart);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        return orderRepository.findWithItemsById(orderId).map(orderMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getOrdersForUser(UUID userId) {
        return orderRepository.findByUserId(userId).stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderDto updateOrder(UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository.findById(updateOrderRequest.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setUpdatedAt(LocalDateTime.now());
        if (updateOrderRequest.getOrderBuyer().getName() != null && order.getCustomerName() != updateOrderRequest.getOrderBuyer().getName()) {
            order.setCustomerName(updateOrderRequest.getOrderBuyer().getName());
        }

        if (updateOrderRequest.getOrderBuyer().getPhoneNumber() != null && !Objects.equals(order.getPhoneNumber(), updateOrderRequest.getOrderBuyer().getPhoneNumber())) {
            order.setPhoneNumber(updateOrderRequest.getOrderBuyer().getPhoneNumber());
        }

        if (updateOrderRequest.getShippingAddress().getAddress() != null && !Objects.equals(order.getShippingAddress().getAddress(), updateOrderRequest.getShippingAddress().getAddress())) {
            order.setShippingAddress(updateOrderRequest.getShippingAddress());
        }
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getOrderStatus().equals("PENDING")) {
            order.setOrderStatus("CANCELLED");
            order = orderRepository.save(order);
        }
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getItems().stream().map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
