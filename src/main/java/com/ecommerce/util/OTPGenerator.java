package com.ecommerce.util;

import com.ecommerce.enums.OTPPurpose;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class OTPGenerator {

    @Value("${spring.mail.auth.username}")
    private String fromEmail;

    JavaMailSender mailSender;

    @Autowired
    public OTPGenerator(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public String generateOTP(int length) {
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * numbers.length());
            otp.append(numbers.charAt(index));
        }
        return otp.toString();
    }

    public MimeMessage sendOTPEmail(String email, String otp, OTPPurpose purpose) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("Your One-Time Password (OTP)" + " for " + purpose.toString());
            String emailContent = "<p>Your OTP is: <b>" + otp + "</b></p>"
                    + "<p>This OTP is valid for the next 10 minutes. Please do not share it with anyone.</p>";
            helper.setText(emailContent, true);
            mailSender.send(message);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create OTP email" + e.getMessage());
        }
    }
}
