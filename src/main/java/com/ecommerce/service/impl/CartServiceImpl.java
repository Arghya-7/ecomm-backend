package com.ecommerce.service.impl;

import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    UserService userService;
    CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(UserService userService, CartRepository cartRepository) {
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createOrGetExistingCart() {
        User user = userService.getUserDetails();
        if(user != null){
            return cartRepository.finByUserIdAndStatus(user.getId(), true).orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setActive(true);
                return cartRepository.save(newCart);
            });
        }
        throw new RuntimeException("User not found");
    }
}
