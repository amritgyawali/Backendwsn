package com.wsn.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "analytics")
public class Analytics {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "metric_date", nullable = false)
    private LocalDate metricDate;

    @Column(name = "metric_type", nullable = false)
    private String metricType;

    @Column(name = "metric_value", nullable = false, precision = 18, scale = 2)
    private BigDecimal metricValue;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Analytics(UUID id, LocalDate metricDate, String metricType, BigDecimal metricValue, LocalDateTime createdAt) {
        this.id = id;
        this.metricDate = metricDate;
        this.metricType = metricType;
        this.metricValue = metricValue;
        this.createdAt = createdAt;
    }

    public Analytics() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getMetricDate() {
        return metricDate;
    }

    public void setMetricDate(LocalDate metricDate) {
        this.metricDate = metricDate;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public BigDecimal getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(BigDecimal metricValue) {
        this.metricValue = metricValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
