package com.ecommerce.service;

import com.ecommerce.model.User;

public interface RegistrationService {
    public boolean otpSend(String email) throws Exception;
    public boolean verifyOtpAndRegisterUser(String email, String otp, User user) throws Exception;
    public boolean canRegister(String email) throws Exception;
}
