package com.wsn.service;

import com.wsn.model.dto.BookingRequest;
import com.wsn.model.dto.PaymentRequest;
import com.wsn.model.entity.Booking;
import com.wsn.model.entity.Inquiry;
import com.wsn.model.entity.Payment;
import com.wsn.model.entity.Vendor;
import com.wsn.repository.BookingRepository;
import com.wsn.repository.InquiryRepository;
import com.wsn.repository.PaymentRepository;
import com.wsn.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final InquiryRepository inquiryRepository;
    private final VendorRepository vendorRepository;

    @Transactional
    public Booking createBookingFromInquiry(BookingRequest request) {
        Inquiry inquiry = inquiryRepository.findById(request.inquiryId())
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        // Check if inquiry is in a valid state for booking
        if (inquiry.getStatus() != Inquiry.Status.RESPONDED && 
            inquiry.getStatus() != Inquiry.Status.CONVERTED) {
            throw new RuntimeException("Inquiry must be in RESPONDED or CONVERTED status before booking. Current status: " + inquiry.getStatus());
        }
        
        // Create booking with all required fields
        Booking booking = Booking.builder()
                .customer(inquiry.getCustomer())
                .vendor(inquiry.getVendor())
                .inquiry(inquiry)
                .serviceType(inquiry.getServiceType())
                .eventDate(inquiry.getEventDate() != null ? inquiry.getEventDate().atStartOfDay() : null)
                .amount(BigDecimal.valueOf(request.amount()))
                .status(Booking.Status.CONFIRMED)
                .build();

        // Update inquiry status to CONVERTED
        inquiry.setStatus(Inquiry.Status.CONVERTED);
        inquiryRepository.save(inquiry);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Payment processPayment(PaymentRequest request){
        Booking booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(()->new RuntimeException("Booking not found"));
        if (request.amount() <= 0){
            throw new RuntimeException("Invalid payment amount");
        }
        Vendor vendor = booking.getVendor();
        
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(BigDecimal.valueOf(request.amount()));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(Payment.Status.SUCCESS);
        // Store the commission rate (percentage) instead of the calculated amount
        payment.setCommission(BigDecimal.valueOf(vendor.getCommission()));

        return paymentRepository.save(payment);
    }

    @Transactional
    public Booking cancelBooking(UUID bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));

        if(booking.getStatus() == Booking.Status.CANCELLED){
            throw new RuntimeException("Booking already cancelled");
        }
        booking.setStatus(Booking.Status.CANCELLED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateBookingStatus(UUID bookingId, Booking.Status status){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public List<Booking> getCustomerBookings(UUID customerId){
        return bookingRepository.findByCustomerId(customerId);
    }

    public List<Booking> getVendorBookings(UUID vendorId){
        return bookingRepository.findByVendorId(vendorId);
    }
}
