package com.ecommerce.service.impl;

import com.ecommerce.enums.OTPPurpose;
import com.ecommerce.helper.OTPHelper;
import com.ecommerce.model.OTPEntity;
import com.ecommerce.repository.OTPRepository;
import com.ecommerce.service.OTPService;
import com.ecommerce.service.UserService;
import com.ecommerce.util.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPServiceImpl implements OTPService {
    OTPGenerator otpGenerator;
    OTPRepository otpRepository;
    JavaMailSender javaMailSender;
    UserService userService;
    OTPHelper otpHelper;
    @Autowired
    public OTPServiceImpl(OTPGenerator otpGenerator, OTPRepository otpRepository, JavaMailSender javaMailSender, UserService userService, OTPHelper otpHelper) {
        this.otpGenerator = otpGenerator;
        this.otpRepository = otpRepository;
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.otpHelper = otpHelper;
    }

    // Registered user will use this method to get OTP
    @Override
    public OTPEntity sendOTP(OTPPurpose purpose) {
        // Dummy implementation for sending OTP
        String email = userService.getUserDetails().getEmail();
        System.out.println("Sending OTP to " + email + " for purpose: " + purpose);
        Optional<OTPEntity> otpEntity = otpRepository.findByEmailAndPurpose(email, purpose);
        return otpHelper.getOtpEntityByEmailAndPurpose(otpEntity, purpose, email);
    }

    // Unregistered user will use this method to get OTP
    @Override
    public OTPEntity sendOTP(OTPPurpose purpose, String email) {
        // Dummy implementation for sending OTP
        System.out.println("Sending OTP to " + email + " for purpose: " + purpose);
        Optional<OTPEntity> otpEntity = otpRepository.findByEmailAndPurpose(email, purpose);
        return otpHelper.getOtpEntityByEmailAndPurpose(otpEntity, purpose, email);
    }

    @Override
    public boolean verifyOTP(String email, String otp, OTPPurpose purpose) {
        // Dummy implementation for verifying OTP
        System.out.println("Verifying OTP " + otp + " for " + email + " for purpose: " + purpose);
        Optional<OTPEntity> otpEntity = otpRepository.findByEmailAndPurpose(email, purpose);
        if (otpEntity.isEmpty()) {
            // OTP not found
            throw new RuntimeException("OTP not found for " + email + " for purpose: " + purpose);
        } else if(otpEntity.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRepository.deleteById(otpEntity.get().getId());
            // OTP expired
            throw new RuntimeException("OTP expired for " + email + " for purpose: " + purpose);
        } else if(!otpEntity.get().getOtp().equals(otp)) {;
            // OTP does not match
            throw new RuntimeException("OTP does not match for " + email + " for purpose: " + purpose);
        } else {
            // OTP verified
            otpRepository.deleteById(otpEntity.get().getId());
            return true;
        }
    }
}
