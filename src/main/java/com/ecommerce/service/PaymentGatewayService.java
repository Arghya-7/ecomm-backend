package com.ecommerce.service;


import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderRequest;

public interface PaymentGatewayService {
    public Object createOrderCashfree(OrderRequest order);
    public Object checkOrderStatus(OrderDetails orderRequest);
}
