package com.ecommerce.repository;

import com.ecommerce.model.payment.CashfreeOrderResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<CashfreeOrderResponse, String> {
    public Optional<CashfreeOrderResponse> findByOrderId(String orderId);
}
