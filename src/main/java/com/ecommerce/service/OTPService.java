package com.ecommerce.service;

import com.ecommerce.enums.OTPPurpose;
import com.ecommerce.model.OTPEntity;

public interface OTPService {
    public OTPEntity sendOTP(OTPPurpose purpose);

    OTPEntity sendOTP(OTPPurpose purpose, String email);

    boolean verifyOTP(String email, String otp, OTPPurpose purpose);
}
