package com.ecommerce.service;

import com.ecommerce.model.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    User createUser(User user) throws  NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    ObjectNode validateUser(User user) throws Exception;
    public UserDetails loadUserByUsername(String username) throws Exception;
    public User getUserDetails();
}
