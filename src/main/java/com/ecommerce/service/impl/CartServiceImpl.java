package com.ecommerce.service.impl;

import com.ecommerce.model.Cart;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {
    UserService userService;
    CartRepository cartRepository;
    ProductService productService;

    @Autowired
    public CartServiceImpl(UserService userService, CartRepository cartRepository, ProductService productService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Cart createOrGetExistingCart() {
        User user = userService.getUserDetails();
        if(user != null){
            Cart cart = cartRepository.finByUserIdAndStatus(user.getId(), true).orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setCreatedAt(new Date());
                newCart.setActive(true);
                newCart.setOrderItemList(new ArrayList<>());
                return cartRepository.save(newCart);
            });
            calculateTotal(cart);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Cart addOrUpdateCartItem(String productId, int quantity) {
        User user = userService.getUserDetails();
        if(user != null){
            Cart cart = createOrGetExistingCart();
            // Logic to add or update cart item goes here
            cart.getOrderItemList().stream()
                        .filter(item -> item.getProductId().equals(productId))
                        .findFirst()
                        .ifPresentOrElse(
                                item -> item.setQuantity(quantity), // Update quantity if item exists
                                () -> cart.getOrderItemList().add(new OrderItem(productId, quantity)) // Add new item
                        );
            calculateTotal(cart);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Cart deleteCartItem(String productId) {
        User user = userService.getUserDetails();
        if(user != null){
            Cart cart = cartRepository.finByUserIdAndStatus(user.getId(), true).orElseThrow(() -> new RuntimeException("Cart not found"));
            cart.getOrderItemList().removeIf(item -> item.getProductId().equals(productId));
            calculateTotal(cart);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public boolean clearCart() {
        User user = userService.getUserDetails();
        if(user != null){
            Cart cart = cartRepository.finByUserIdAndStatus(user.getId(), true).orElseThrow(() -> new RuntimeException("Cart not found"));
            cart.getOrderItemList().clear();
            calculateTotal(cart);
            cartRepository.save(cart);
            return true;
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Cart incrementCartItemByOne(String productId) {
        User user = userService.getUserDetails();
        if(user != null){
            Cart cart = createOrGetExistingCart();
            // Logic to increment cart item quantity goes here
            cart.getOrderItemList().stream()
                        .filter(item -> item.getProductId().equals(productId))
                        .findFirst()
                        .ifPresentOrElse(
                                item -> item.setQuantity(item.getQuantity() + 1), // Increment quantity if item exists
                                () -> cart.getOrderItemList().add(new OrderItem(productId, 1)) // Add new item
                        );
            calculateTotal(cart);
            return cartRepository.save(cart);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Cart inactiveCart(){
        User user = userService.getUserDetails();
        if(user != null){
            Cart cart = cartRepository.finByUserIdAndStatus(user.getId(), true).orElseThrow(() -> new RuntimeException("Cart not found"));
            cart.setActive(false);
            Cart inactivteCart = cartRepository.save(cart);
            // Create a Blank cart for the user
            createOrGetExistingCart();
            return inactivteCart;
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Cart getCart() {
        User user = userService.getUserDetails();
        if(user != null) {
            Cart cart = cartRepository.finByUserIdAndStatus(user.getId(), true).orElse(new Cart());
            return cart;
        }
        throw  new RuntimeException("User not found");
    }

    private void calculateTotal(Cart cart) {
        if(cart != null && cart.getOrderItemList() == null){
            cart.setTotalPrice(0);
        }else if(cart != null && cart.getOrderItemList()!= null){
            double total = cart.getOrderItemList().stream()
                    .mapToDouble(item -> productService.getTotalPrice(item.getProductId(), item.getQuantity()))
                    .sum();
            cart.setTotalPrice(total);
        }
    }
}
