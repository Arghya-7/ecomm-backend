package com.ecommerce.service.impl;

import com.ecommerce.components.EncryptionService;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    EncryptionService encryptionService;
    ObjectMapper objectMapper;
    JwtUtil jwtUtil;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, EncryptionService encryptionService, ObjectMapper objectMapper,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User createUser(User user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(userRepository.findById(user.getId()).isPresent()){
            throw new RuntimeException("User with ID " + user.getId() + " already exists.");
        }
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public ObjectNode validateUser(User user) throws Exception {
        if(userRepository.findById(user.getId()).isPresent()){
            User existingUser = userRepository.findById(user.getId()).get();
            String decryptedPassword = encryptionService.decrypt(existingUser.getPassword());
            if(decryptedPassword.equals(user.getPassword())){
                return objectMapper.createObjectNode().put("token", jwtUtil.generateToken(existingUser.getId()));
            } else {
                throw new RuntimeException("Invalid credentials for user ID " + user.getId());
            }
        } else {
            throw new RuntimeException("User with ID " + user.getId() + " does not exist.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws Exception {
        System.out.println(username);
        User user = userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new RuntimeException("User not found with email: " + username);
        } else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .build();
        }
    }
}
