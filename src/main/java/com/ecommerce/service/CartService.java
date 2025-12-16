package com.ecommerce.service;

import com.ecommerce.model.Cart;

public interface CartService {
    public Cart createOrGetExistingCart();

    Cart addOrUpdateCartItem(String productId, int quantity);

    Cart deleteCartItem(String productId);

    boolean clearCart();

    Cart incrementCartItemByOne(String productId);

    Cart inactiveCart();
}
