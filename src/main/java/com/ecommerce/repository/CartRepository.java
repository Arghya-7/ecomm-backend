package com.ecommerce.repository;

import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    @Query("{ 'user.$id' : ?0 , 'isActive' : ?1 }")
    Optional<Cart> finByUserIdAndStatus(String userId, boolean isActive);
}
