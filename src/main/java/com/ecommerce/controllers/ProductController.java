package com.ecommerce.controllers;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping({"/products", "/products/"})
    public ResponseEntity<List<Product>> findAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        // Implementation to get product by ID
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable("name") String name) {
        // Implementation to get product by name
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "") String text) {
        // Implementation to get paginated products
        return ResponseEntity.ok(productService.getProductsPaginated(page, size, text));
    }
}
