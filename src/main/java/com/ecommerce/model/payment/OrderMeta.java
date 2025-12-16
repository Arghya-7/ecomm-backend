package com.ecommerce.model.payment;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderMeta {

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("payment_methods")
    private String paymentMethods;

    // getters & setters
}
