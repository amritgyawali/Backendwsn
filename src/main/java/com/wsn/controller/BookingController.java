package com.wsn.controller;

import com.wsn.model.dto.BookingRequest;
import com.wsn.model.dto.BookingResponse;
import com.wsn.model.dto.PaymentRequest;
import com.wsn.model.dto.PaymentResponse;
import com.wsn.model.entity.Booking;
import com.wsn.model.entity.Payment;
import com.wsn.security.UserDetailsImpl;
import com.wsn.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        Booking booking = bookingService.createBookingFromInquiry(request);
        return ResponseEntity.ok(BookingResponse.fromEntity(booking));
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable UUID id,
            @RequestBody PaymentRequest request) {
        if (!id.equals(request.bookingId())) {
            throw new RuntimeException("Booking ID mismatch");
        }

        Payment payment = bookingService.processPayment(request);
        return ResponseEntity.ok(PaymentResponse.fromEntity(payment));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable UUID id) {
        Booking booking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(BookingResponse.fromEntity(booking));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @PathVariable UUID id,
            @RequestBody String status) {
        try {
            // Remove any surrounding quotes and trim whitespace
            String cleanStatus = status.replaceAll("^\"|\"$", "").trim();
            Booking.Status newStatus = Booking.Status.valueOf(cleanStatus.toUpperCase());
            Booking booking = bookingService.updateBookingStatus(id, newStatus);
            return ResponseEntity.ok(BookingResponse.fromEntity(booking));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Valid statuses are: PENDING, CONFIRMED, COMPLETED, CANCELLED");
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<List<BookingResponse>> getCustomerBookings() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<Booking> bookings = bookingService.getCustomerBookings(user.getId());
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor")
    public ResponseEntity<List<BookingResponse>> getVendorBookings() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<Booking> bookings = bookingService.getVendorBookings(user.getId());
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}