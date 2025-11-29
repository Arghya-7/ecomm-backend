package com.ecommerce.service.impl;


import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderRequest;
import com.ecommerce.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Value("${cashfree.app.id}")
    private String appId;

    @Value("${cashfree.secret.key}")
    private String secretKey;

    @Value("${cashfree.url}")
    private String cashfreeUrl;

    @Value("${cashfree.return.url}")
    private String returnUrl;


    @Override
    public Object createOrderCashfree(OrderRequest order) {
        try {
            System.out.println("Request comes");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-client-id", appId);
            headers.set("x-client-secret", secretKey);
            headers.set("x-api-version", "2022-09-01");

            String orderId = "ORDER" + UUID.randomUUID().toString().replace("-","");
            Double amount = Double.parseDouble(Integer.toString(order.getAmount()));

            Map<String, Object> customer = Map.of(
                    "customer_id", order.getMail().substring(0, order.getMail().indexOf("@")),
                    "customer_email", order.getMail(),
                    "customer_phone", order.getContactNumber()
            );

            Map<String, Object> body = Map.of(
                    "order_id", orderId,
                    "order_amount", amount,
                    "order_currency", order.getCurrency(),
                    "customer_details", customer,
                    "return_url" , returnUrl
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(cashfreeUrl, entity, Map.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @Override
    public Object checkOrderStatus(OrderDetails orderRequest) {
        String url = "https://sandbox.cashfree.com/pg/orders/" + orderRequest.getOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-version", "2022-09-01");
        headers.set("x-client-id", appId);
        headers.set("x-client-secret", secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
        );

        return response.getBody();
    }
}
