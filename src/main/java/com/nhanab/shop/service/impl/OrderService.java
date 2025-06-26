package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.model.Order;
import com.nhanab.shop.model.Payment;
import com.nhanab.shop.model.User;
import com.nhanab.shop.repository.OrderRepository;
import com.nhanab.shop.repository.PaymentRepository;
import com.nhanab.shop.repository.UserRepository;
import com.nhanab.shop.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartService cartService;
    private final ProductServiceImpl productService;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        // Lấy user
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo order
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setUser(user);

        // Lưu trước để lấy ID
        order = orderRepository.save(order);

        // Tạo payment
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod("COD"); // ví dụ
        payment.setPaymentStatus("PENDING");
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        // Gán lại nếu muốn bidirectional
        order.setPayment(payment);
    }

}
