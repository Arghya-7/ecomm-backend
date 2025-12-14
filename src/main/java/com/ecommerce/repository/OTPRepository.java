package com.ecommerce.repository;

import com.ecommerce.enums.OTPPurpose;
import com.ecommerce.model.OTPEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends MongoRepository<OTPEntity, String> {
    Optional<OTPEntity> findByEmailAndPurpose(String email, OTPPurpose purpose);
}
