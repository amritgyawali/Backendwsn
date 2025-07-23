package com.wsn.repository;

import com.wsn.model.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, UUID> {
    Optional<Analytics> findByMetricDateAndMetricType(LocalDate metricDate, String metricType);
}
