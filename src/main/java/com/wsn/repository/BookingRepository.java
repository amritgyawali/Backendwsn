package com.wsn.repository;

import com.wsn.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    long countByVendorIdAndStatus(UUID vendorId,Booking.Status status);
    List<Booking> findByVendorIdAndEventDateBetween(UUID vendorId, LocalDate start, LocalDate end);
    long countByCustomerId(UUID customerId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'CONFIRMED' AND b.eventDate >= :date")
    long countActiveBookings(LocalDate date);
    List<Booking> findByCustomerId(UUID customerId);
}
