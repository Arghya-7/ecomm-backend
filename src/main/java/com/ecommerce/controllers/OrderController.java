package com.ecommerce.controllers;

import com.ecommerce.enums.PAYMENT_METHOD;
import com.ecommerce.enums.PAYMENT_STATUS;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping({"/createFromCart/{payment_method}", "/createFromCart/{payment_method}/{payment_id}"})
    public ResponseEntity<Order> createOrderFromCart(@PathVariable(value = "payment_method") PAYMENT_METHOD paymentMethod,
                                                        @PathVariable(value = "payment_id", required = false) String paymentId) {
        try{
            if(paymentId != null){
                return ResponseEntity.ok(orderService.createOrderFromCart(paymentMethod, paymentId));
            } else {
                return ResponseEntity.ok(orderService.createOrderFromCart(paymentMethod));
            }
        } catch (Exception er){
            throw new RuntimeException(er.getMessage());
        }
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("order_id") String orderId) {
        try {
            return ResponseEntity.ok(orderService.findById(orderId));
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getOrdersForUser() {
        try {
            return ResponseEntity.ok(orderService.getOrdersForUser());
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @PutMapping("/changePaymentStatus/{payment_id}/{payment_status}")
    public ResponseEntity<Order> changePaymentStatus(@PathVariable("payment_id") String paymentId,
                                                     @PathVariable("payment_status") PAYMENT_STATUS paymentStatus) {
        try {
            return ResponseEntity.ok(orderService.changePaymentStatus(paymentId, paymentStatus));
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @GetMapping("/byPaymentId/{payment_id}")
    public ResponseEntity<Order> getOrderByPaymentId(@PathVariable("payment_id") String paymentId) {
        try {
            return ResponseEntity.ok(orderService.findByPaymentId(paymentId));
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }
}
