package com.ecommerce.service;

import com.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);
    public List<Product> getAllProducts();
    public Product getProductById(String id);
}
