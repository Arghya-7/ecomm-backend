package com.ecommerce.service.impl;

import com.ecommerce.components.EncryptionService;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

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
        if(user.getId()!= null && userRepository.findById(user.getId()).isPresent()){
            throw new RuntimeException("User with ID " + user.getId() + " already exists.");
        } else if (user.getEmail()!= null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists.");
        }
        user.setId(user.getId() + UUID.randomUUID().toString().replace("-",""));;
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public ObjectNode validateUser(User user) throws Exception {
        if(user.getId() != null && userRepository.findById(user.getId()).isPresent()){
            User existingUser = userRepository.findById(user.getId()).get();
            String decryptedPassword = encryptionService.decrypt(existingUser.getPassword());
            if(decryptedPassword.equals(user.getPassword())){
                return objectMapper.createObjectNode().put("token", jwtUtil.generateToken(existingUser.getId()));
            } else {
                throw new RuntimeException("Invalid credentials for user ID " + user.getId());
            }
        } else if(user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()){
            User existingUser = userRepository.findByEmail(user.getEmail()).get();
            String decryptedPassword = encryptionService.decrypt(existingUser.getPassword());
            if(decryptedPassword.equals(user.getPassword())){
                return objectMapper.createObjectNode().put("token", jwtUtil.generateToken(existingUser.getId()));
            } else {
                throw new RuntimeException("Invalid credentials for user ID " + user.getId());
            }
        }  else {
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

    @Override
    public User getUserDetails(){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetails user =
                (UserDetails) auth.getPrincipal();
        System.out.println(user);
        return userRepository.findByEmail(user.getUsername()).orElseThrow(() ->new RuntimeException("User not found"));
    }
}
