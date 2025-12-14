package com.ecommerce.controllers;

import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/cart")
public class CartController {
    CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/")
    public ResponseEntity<Cart> createOrGetCart() {
        try {
            Cart cart = cartService.createOrGetExistingCart();
            return ResponseEntity.ok(cart);
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }
}
