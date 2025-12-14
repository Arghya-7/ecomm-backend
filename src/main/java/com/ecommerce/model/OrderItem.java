package com.ecommerce.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {
    @DBRef(lazy = true)
    private Product product;
    private int quantity;
}
