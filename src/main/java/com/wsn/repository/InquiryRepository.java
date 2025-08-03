package com.wsn.repository;

import com.wsn.model.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InquiryRepository extends JpaRepository<Inquiry, UUID> {
    List<Inquiry> findByVendorIdAndStatus(UUID vendorId, Inquiry.Status status);
    long countByVendorIdAndStatus(UUID vendorId, Inquiry.Status status);

    List<Inquiry> findByCustomerId(UUID customerId);
}
