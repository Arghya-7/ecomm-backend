package com.ecommerce.model;

import com.ecommerce.enums.PAYMENT_METHOD;
import com.ecommerce.enums.PAYMENT_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private String paymentId;
    private String cartId;
    private PAYMENT_STATUS paymentStatus;
    @DBRef(lazy = true)
    private User user;
    private Date initiatedAt;
    private Date completedAt;
    private List<OrderItem> orderItemList;
    private PAYMENT_METHOD paymentMethod;
    private double totalPrice;
}
