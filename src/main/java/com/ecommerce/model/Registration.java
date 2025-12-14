package com.ecommerce.model;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Registration {
    private String email;
    private String otp;
    private User user;
}
