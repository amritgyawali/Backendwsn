package com.wsn.repository;

import com.wsn.model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
    long countByActive(boolean active);
    List<Vendor> findByActiveTrueAndServiceType(String serviceType);
}
