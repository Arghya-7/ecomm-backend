package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("""
    {
      $or: [
        { name: { $regex: ?0, $options: 'i' } },
        { description: { $regex: ?0, $options: 'i' } }
      ]
    }
    """)
    Page<Product> searchByText(String keyword, Pageable pageable);
}
