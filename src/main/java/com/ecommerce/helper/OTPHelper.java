package com.ecommerce.helper;

import com.ecommerce.enums.OTPPurpose;
import com.ecommerce.model.OTPEntity;
import com.ecommerce.repository.OTPRepository;
import com.ecommerce.util.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OTPHelper {
    OTPRepository otpRepository;
    OTPGenerator otpGenerator;

    @Autowired
    public OTPHelper(OTPRepository otpRepository, OTPGenerator otpGenerator) {
        this.otpGenerator = otpGenerator;
        this.otpRepository = otpRepository;
    }

    public OTPEntity getOtpEntityByEmailAndPurpose(Optional<OTPEntity> otpEntity, OTPPurpose purpose, String email) {
        if(otpEntity.isPresent() && otpEntity.get().getExpiresAt().isAfter(LocalDateTime.now())){
            // OTP Still valid
            otpGenerator.sendOTPEmail(email,otpEntity.get().getOtp(), purpose);
            return otpEntity.get();
        } else {
            // delete existing OTP entry  ifpresent
            otpEntity.ifPresent(entity -> otpRepository.deleteById(entity.getId()));
            // create new OTP entry
            String otp = otpGenerator.generateOTP(6);
            OTPEntity newOtpEntity = new OTPEntity();
            newOtpEntity.setEmail(email);
            newOtpEntity.setPurpose(purpose);
            newOtpEntity.setOtp(otp);
            newOtpEntity.setCreatedAt(LocalDateTime.now());
            newOtpEntity.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            otpRepository.save(newOtpEntity);
            System.out.println("Creating new OTP entry for " + email);
            otpGenerator.sendOTPEmail(email,otp, purpose);
            return newOtpEntity;
        }
    }

}
