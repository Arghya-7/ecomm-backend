package com.ecommerce.repository;

import com.ecommerce.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByPaymentId(String paymentId);
    Optional<Order> findByCartId(String cartId);
    @Query(
            value = "{ 'user.id': ?0 }",
            sort  = "{ 'initiatedAt': -1 }"
    )
    List<Order> findByUserIdOrderByInitiatedAtDesc(String userId);
}
