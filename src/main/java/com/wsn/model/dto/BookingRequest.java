package com.wsn.model.dto;

import java.util.UUID;

public record BookingRequest(
      UUID inquiryId,
      double amount,
      String notes
) {}
