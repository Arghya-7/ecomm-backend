package com.ecommerce.model;

import lombok.Data;

@Data
public class OrderRequest {
    private int amount;
    private String currency;
    private String mail;
    private String contactNumber;
}
