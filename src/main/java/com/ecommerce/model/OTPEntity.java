package com.ecommerce.model;

import com.ecommerce.enums.OTPPurpose;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document("email_otps")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@CompoundIndex(
        name = "email_purpose_idx",
        def = "{'email': 1, 'purpose': 1}",
        unique = true
)
public class OTPEntity {
    @Id
    private String id;
    private String email;
    private String otp;
    private OTPPurpose purpose;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
