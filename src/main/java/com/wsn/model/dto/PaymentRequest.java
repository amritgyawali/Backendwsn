package com.wsn.model.dto;

import java.util.UUID;

public record PaymentRequest(
        UUID bookingId,
        double amount
) {}
