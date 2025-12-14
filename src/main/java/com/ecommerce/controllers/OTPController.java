package com.ecommerce.controllers;

import com.ecommerce.enums.OTPPurpose;
import com.ecommerce.model.OTPEntity;
import com.ecommerce.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OTPController {
    OTPService otpService;
    JavaMailSender mailSender;

    @Autowired
    public OTPController(OTPService otpService, JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.otpService = otpService;
    }
    @PostMapping("/send/{purpose}")
    public OTPEntity sendOTP(@PathVariable("purpose") OTPPurpose purpose) {
        return otpService.sendOTP(purpose);
    }
}
