package com.ecommerce.controllers;

import com.ecommerce.model.Registration;
import com.ecommerce.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/registration")
@CrossOrigin("*")
public class RegistrationController {
    RegistrationService registrationService;
    ObjectMapper objectMapper;

    @Autowired
    public RegistrationController(RegistrationService registrationService, ObjectMapper objectMapper) {
        this.registrationService = registrationService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<ObjectNode> otpSend(@RequestBody Registration registration) throws Exception {
        try {
            boolean result = registrationService.otpSend(registration.getEmail());
            return ResponseEntity.ok(objectMapper.createObjectNode().put("message","OTP sent successfully").put("success", result));
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @PostMapping("/verify")
    public boolean verifyOtpAndRegisterUser(@RequestBody Registration registration) throws Exception {
        try{
            return registrationService.verifyOtpAndRegisterUser(registration.getEmail(), registration.getOtp(), registration.getUser());
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }

    @GetMapping("/isAlreadyUser/{email}")
    public ResponseEntity<ObjectNode> canRegister(@PathVariable("email") String email) {
        try {
            registrationService.canRegister(email);
            return ResponseEntity.ok(objectMapper.createObjectNode().put("message", "user can register with this email")
                            .put("email", email)
                            .put("canRegister", true));
        } catch (Exception er) {
            throw new RuntimeException(er.getMessage());
        }
    }
}
