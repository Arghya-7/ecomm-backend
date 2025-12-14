package com.ecommerce.service.impl;

import com.ecommerce.enums.OTPPurpose;
import com.ecommerce.model.User;
import com.ecommerce.service.OTPService;
import com.ecommerce.service.RegistrationService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    OTPService otpService;
    UserService userService;

    @Autowired
    public RegistrationServiceImpl(OTPService otpService, UserService userService) {
        this.otpService = otpService;
        this.userService = userService;
    }

    @Override
    public boolean otpSend(String email) throws Exception {
        otpService.sendOTP(OTPPurpose.REGISTRATION, email);
        return true;
    }

    @Override
    public boolean verifyOtpAndRegisterUser(String email, String otp, User user) throws Exception {
        boolean isVerified = otpService.verifyOTP(email, otp, OTPPurpose.REGISTRATION);
        if (isVerified) {
            // Logic to register the user goes here
            userService.createUser(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canRegister(String email) throws Exception {
        if (userService.isEmailRegistered(email)) {;
            throw new RuntimeException("User already exists with email: " + email);
        }
        return true;
    }
}
