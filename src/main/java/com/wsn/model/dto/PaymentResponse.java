package com.wsn.model.dto;

import com.wsn.model.entity.Payment;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PaymentResponse(
    UUID id,
    UUID bookingId,
    BigDecimal amount,
    LocalDateTime paymentDate,
    Payment.Status status,
    BigDecimal commissionRate,
    LocalDateTime createdAt
) {
    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
            .id(payment.getId())
            .bookingId(payment.getBooking() != null ? payment.getBooking().getId() : null)
            .amount(payment.getAmount())
            .paymentDate(payment.getPaymentDate())
            .status(payment.getStatus())
            .commissionRate(payment.getCommission())
            .createdAt(payment.getCreatedAt())
            .build();
    }
}
