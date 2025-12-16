package com.ecommerce.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("cashfree_order_responses")
public class CashfreeOrderResponse {
    @JsonProperty("cf_order_id")
    private Long cfOrderId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;

    private String entity;

    @JsonProperty("order_amount")
    private Double orderAmount;

    @JsonProperty("order_currency")
    private String orderCurrency;

    @JsonProperty("order_expiry_time")
    private String orderExpiryTime;

    @Id
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_meta")
    private OrderMeta orderMeta;

    @JsonProperty("order_note")
    private String orderNote;

    @JsonProperty("order_splits")
    private List<Object> orderSplits;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("order_tags")
    private Object orderTags;

    @JsonProperty("payment_session_id")
    private String paymentSessionId;

    private Payments payments;
    private Refunds refunds;
    private Settlements settlements;

    @JsonProperty("terminal_data")
    private Object terminalData;

    // getters & setters
}

