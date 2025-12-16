package com.ecommerce.controllers;

import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/cart")
public class CartController {
    CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping()
    public ResponseEntity<Cart> createOrGetCart() {
        try {
            Cart cart = cartService.createOrGetExistingCart();
            return ResponseEntity.ok(cart);
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @PutMapping("/{productId}/{quantity}")
    public ResponseEntity<Cart> addOrUpdateCartItem(@PathVariable("productId") String productId,@PathVariable("quantity") int quantity) {
        try {
            Cart cart = cartService.addOrUpdateCartItem(productId, quantity);
            return ResponseEntity.ok(cart);
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @PutMapping("/clear")
    public ResponseEntity<Boolean> clearCart() {
        try {
            boolean result = cartService.clearCart();
            return ResponseEntity.ok(result);
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable("productId") String productId) {
        try {
            Cart cart = cartService.deleteCartItem(productId);
            return ResponseEntity.ok(cart);
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @PutMapping("/increment/{productId}")
    public ResponseEntity<Cart> incrementCartItemByOne(@PathVariable("productId") String productId) {
        try {
            Cart cart = cartService.incrementCartItemByOne(productId);
            return ResponseEntity.ok(cart);
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }
}
