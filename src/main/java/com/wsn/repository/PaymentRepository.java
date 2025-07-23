package com.wsn.repository;

import com.wsn.model.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end AND p.status = 'SUCCESS'")
    long sumSuccessfulPaymentBetween(LocalDateTime start, LocalDateTime end);

    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
}
