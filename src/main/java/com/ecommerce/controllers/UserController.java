package com.ecommerce.controllers;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    UserService userService;
    ObjectMapper objectMapper;
    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper){
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/register" )
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception er){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/login")
    public ResponseEntity<ObjectNode> validateUser(@RequestBody User user) {
        // Implementation for user validation
        try{
            return ResponseEntity.ok(userService.validateUser(user));
        } catch (Exception er){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
