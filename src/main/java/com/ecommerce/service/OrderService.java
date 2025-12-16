package com.ecommerce.service;

import com.ecommerce.enums.PAYMENT_METHOD;
import com.ecommerce.enums.PAYMENT_STATUS;
import com.ecommerce.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrderFromCart(PAYMENT_METHOD paymentMethod);

    Order createOrderFromCart(PAYMENT_METHOD paymentMethod, String paymentId);

    Order findById(String orderId);

    List<Order> getOrdersForUser();

    Order changePaymentStatus(String paymentId, PAYMENT_STATUS paymentStatus);

    Order findByPaymentId(String paymentId);
}
