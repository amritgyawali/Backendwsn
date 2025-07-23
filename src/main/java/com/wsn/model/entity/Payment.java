package com.wsn.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(precision = 18, scale = 2)
    private BigDecimal amount;
    
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(precision = 5, scale = 2)
    private BigDecimal commission; // in percentage

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum Status {
        PENDING, SUCCESS, FAILED
    }
}