package com.ecommerce.service.impl;

import com.ecommerce.enums.PAYMENT_METHOD;
import com.ecommerce.enums.PAYMENT_STATUS;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    CartService cartService;
    OrderRepository orderRepository;
    UserService userService;

    @Autowired
    public OrderServiceImpl(CartService cartService, OrderRepository orderRepository, UserService userService) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Order createOrderFromCart(PAYMENT_METHOD paymentMethod) {
        // Implementation to create order from cart
        Cart cart = cartService.inactiveCart();
        Order order = new Order();
        order.setCartId(cart.getId());
        order.setOrderItemList(cart.getOrderItemList());
        order.setTotalPrice(cart.getTotalPrice());
        order.setUser(userService.getUserDetails());
        order.setInitiatedAt(new Date());
        order.setPaymentStatus(PAYMENT_STATUS.PENDING);
        order.setPaymentMethod(paymentMethod);
        return orderRepository.save(order);
    }


    @Override
    public Order createOrderFromCart(PAYMENT_METHOD paymentMethod, String paymentId) {
        // Implementation to create order from cart
        Cart cart = cartService.inactiveCart();
        Order order = new Order();
        order.setCartId(cart.getId());
        order.setOrderItemList(cart.getOrderItemList());
        order.setTotalPrice(cart.getTotalPrice());
        order.setUser(userService.getUserDetails());
        order.setInitiatedAt(new Date());
        order.setPaymentStatus(PAYMENT_STATUS.PENDING);
        order.setPaymentId(paymentId);
        order.setPaymentMethod(paymentMethod);
        return orderRepository.save(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersForUser() {
        User user = userService.getUserDetails();
        return orderRepository.findByUserIdOrderByInitiatedAtDesc(userService.getUserDetails().getId());
    }

    @Override
    public Order changePaymentStatus(String paymentId, PAYMENT_STATUS paymentStatus) {
        Order order = orderRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Order not found for payment ID: " + paymentId));
        order.setPaymentStatus(paymentStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order findByPaymentId(String paymentId) {
        return orderRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Order not found for payment ID: " + paymentId));
    }
}
