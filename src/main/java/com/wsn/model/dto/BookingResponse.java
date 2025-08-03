package com.wsn.model.dto;

import com.wsn.model.entity.Booking;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record BookingResponse(
    UUID id,
    UUID customerId,
    UUID vendorId,
    UUID inquiryId,
    String serviceType,
    LocalDateTime eventDate,
    BigDecimal amount,
    Booking.Status status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static BookingResponse fromEntity(Booking booking) {
        return BookingResponse.builder()
            .id(booking.getId())
            .customerId(booking.getCustomer() != null ? booking.getCustomer().getId() : null)
            .vendorId(booking.getVendor() != null ? booking.getVendor().getId() : null)
            .inquiryId(booking.getInquiry() != null ? booking.getInquiry().getId() : null)
            .serviceType(booking.getServiceType())
            .eventDate(booking.getEventDate())
            .amount(booking.getAmount())
            .status(booking.getStatus())
            .createdAt(booking.getCreatedAt())
            .updatedAt(booking.getUpdatedAt())
            .build();
    }
}
