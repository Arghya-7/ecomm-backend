package com.ecommerce.service;

import com.ecommerce.model.Cart;

public interface CartService {
    public Cart createOrGetExistingCart();
}
