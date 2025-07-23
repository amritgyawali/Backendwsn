package com.wsn.service;

import com.wsn.model.entity.Analytics;
import com.wsn.model.entity.Booking;
import com.wsn.model.entity.Customer;
import com.wsn.model.entity.Vendor;
import com.wsn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private AnalyticsRepository analyticsRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void aggregateDailyMetrics(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        aggregateMetric(yesterday, "TOTAL_USERS", BigDecimal.valueOf(userRepository.count()));
        aggregateMetric(yesterday, "ACTIVE_VENDORS", BigDecimal.valueOf(vendorRepository.countByActive(true)));
        aggregateMetric(yesterday, "TOTAL_REVENUE", calculateDailyRevenue(yesterday));
        aggregateMetric(yesterday, "ACTIVE_BOOKINGS", BigDecimal.valueOf(bookingRepository.countActiveBookings(yesterday)));
    }

    private BigDecimal calculateDailyRevenue(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return BigDecimal.valueOf(paymentRepository.sumSuccessfulPaymentBetween(start, end));
    }


    private void aggregateMetric(LocalDate date, String metricType, BigDecimal value){
        Optional<Analytics> existing = analyticsRepository.findByMetricDateAndMetricType(date,metricType);
        if (existing.isPresent()){
            Analytics metric = existing.get();
            metric.setMetricValue(value);
            analyticsRepository.save(metric);
        }else {
            Analytics metric = new Analytics();
            metric.setMetricDate(date);
            metric.setMetricType(metricType);
            metric.setMetricValue(value);
            analyticsRepository.save(metric);
        }
    }

//    private double calculateDailyRevenue(LocalDate date){
//        LocalDateTime start = date.atStartOfDay();
//        LocalDateTime end = date.plusDays(1).atStartOfDay();
//        return paymentRepository.sumSuccessfulPaymentBetween(start,end);
//    }

    // Admin Dashboard Metrics
    public Map<String, Object> getAdminDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        LocalDate today = LocalDate.now();

        // Total Users
        metrics.put("totalUsers", userRepository.count());

        // Active Vendors
        metrics.put("activeVendors", vendorRepository.countByActive(true));

        // Monthly Revenue
        LocalDateTime startOfMonth = LocalDateTime.of(today.withDayOfMonth(1), LocalTime.MIN);
        metrics.put("monthlyRevenue", BigDecimal.valueOf(paymentRepository.sumSuccessfulPaymentBetween(
                startOfMonth, LocalDateTime.now())));

        // Active Bookings
        metrics.put("activeBookings", bookingRepository.countActiveBookings(today));

        // Uptime (simulated)
        metrics.put("uptime", 99.9);

        // Active Alerts (to be implemented)
        metrics.put("activeAlerts", 3);

        return metrics;
    }

    // Vendor Dashboard Metrics
    public Map<String, Object> getVendorDashboardMetrics(UUID vendorId) {
        Map<String, Object> metrics = new HashMap<>();
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();

        // Monthly Revenue
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        
        // Calculate total revenue using BigDecimal for precision
        BigDecimal revenue = bookingRepository.findByVendorIdAndEventDateBetween(vendorId,
                        startOfMonth, endOfMonth).stream()
                .map(Booking::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        metrics.put("monthlyRevenue", revenue);

        // Bookings
        metrics.put("bookings", bookingRepository.countByVendorIdAndStatus(
                vendorId, Booking.Status.CONFIRMED));

        // Average Rating (from vendor entity)
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow();
        metrics.put("averageRating", vendor.getRating());

        // Response Rate
        metrics.put("responseRate", vendor.getResponseRate());

        return metrics;
    }

    // Customer Dashboard Metrics
    public Map<String, Object> getCustomerDashboardMetrics(UUID customerId) {
        Map<String, Object> metrics = new HashMap<>();
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        // Days Until Wedding
        LocalDate weddingDate = customer.getWeddingDate().toLocalDate();
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), weddingDate);
        metrics.put("daysUntilWedding", daysUntil);

        // Budget Spent
        BigDecimal spent = bookingRepository.findByCustomerId(customerId).stream()
                .map(Booking::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        metrics.put("budgetSpent", spent);

        // Vendors Booked
        metrics.put("vendorsBooked", bookingRepository.countByCustomerId(customerId));

        return metrics;
    }
}
