package com.ecommerce.controllers;


import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderRequest;
import com.ecommerce.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cashfree-controller")
@CrossOrigin("*")
public class PayFreeController {
    private PaymentGatewayService paymentGatewayService;

    @Autowired
    public PayFreeController(PaymentGatewayService paymentGatewayService){
        this.paymentGatewayService = paymentGatewayService;
    }

    @PostMapping("/create-order-cashfree")
    public Object createOrder(@RequestBody OrderRequest order) {
        try{
            Object object = paymentGatewayService.createOrderCashfree(order);
            System.out.println(object);
            return object;
        } catch (Exception er){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/check-order-status")
    public Object checkOrderStatus(@RequestBody OrderDetails orderDetails) {
        try{
            Object object = paymentGatewayService.checkOrderStatus(orderDetails);
            System.out.println(object);
            return object;
        } catch (Exception er){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
