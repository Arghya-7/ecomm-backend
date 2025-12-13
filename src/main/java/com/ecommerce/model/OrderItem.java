package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {
    private Product product;
    private int quantity;

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
