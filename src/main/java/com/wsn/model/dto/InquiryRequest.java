package com.wsn.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record InquiryRequest(
        UUID vendorId,
        String serviceType,
        LocalDate eventDate,
        Double budget,
        String notes

) {}
