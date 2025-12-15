package com.ecommerce.service.impl;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MongoTemplate mongoTemplate){
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProductByName(String name) {
        if(name == null || name.isEmpty()){
            return getAllProducts();
        }
        IndexOperations indexOps = mongoTemplate.indexOps("products");
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(name);
        Query query = TextQuery.queryText(criteria).sortByScore();
        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public double getTotalPrice(String productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        double price = product != null ? product.getPrice() : 0.0;
        return price * quantity;
    }
}
