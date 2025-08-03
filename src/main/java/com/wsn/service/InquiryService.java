package com.wsn.service;

import com.wsn.model.dto.BookingRequest;
import com.wsn.model.dto.InquiryRequest;
import com.wsn.model.entity.Booking;
import com.wsn.model.entity.Customer;
import com.wsn.model.entity.Inquiry;
import com.wsn.model.entity.Vendor;
import com.wsn.repository.CustomerRepository;
import com.wsn.repository.InquiryRepository;
import com.wsn.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class InquiryService {
    @Autowired
    private InquiryRepository inquiryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;

    @Transactional
    public Inquiry createInquiry(UUID customerId, InquiryRequest request){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Vendor vendor = vendorRepository.findById(request.vendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setCustomer(customer);
        inquiry.setVendor(vendor);
        inquiry.setServiceType(request.serviceType());
        inquiry.setEventDate(request.eventDate());
        inquiry.setBudget(request.budget());
        inquiry.setNotes(request.notes());

        return inquiryRepository.save(inquiry);
    }

    @Transactional
    public Inquiry respondToInquiry(UUID inquiryId, String responseMessage){
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(()-> new RuntimeException("Inquiry not found"));
        inquiry.setStatus(Inquiry.Status.RESPONDED);
        inquiry.setNotes(inquiry.getNotes()+ "\n Vendor Response: "+ responseMessage);
        return inquiryRepository.save(inquiry);
    }

    @Transactional
    public Inquiry convertToBooking(UUID inquiryId){
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(()-> new RuntimeException("Inquiry Not found"));
        inquiry.setStatus(Inquiry.Status.CONVERTED);
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getCustomerInquiries(UUID customerId){
        return inquiryRepository.findByCustomerId(customerId);
    }

    public List<Inquiry> getVendorInquiries(UUID vendorId){
        return inquiryRepository.findByVendorIdAndStatus(vendorId , Inquiry.Status.OPEN);
    }

//    @Transactional
//    public Booking createBookingFromInquiry(BookingRequest request){
//        Inquiry inquiry = inquiryRepository.findById(request.inquiryId())
//                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
//        if (inquiry.getStatus() != Inquiry.Status.RESPONDED){
//            throw new RuntimeException("Inquiry must be responded before booking");
//        }
//
//        Booking booking = new Booking(
//                inquiry.getCustomer(),
//                inquiry.getVendor(),
//                inquiry
//        );
//
//        booking.setAmount(BigDecimal.valueOf(request.amount()));
//        booking.setStatus(Booking.Status.CONFIRMED);
//
//        inquiry.setStatus(Inquiry.Status.CONVERTED);
//        inquiryRepository.save(inquiry);
//
//        return bookingRepository.save(booking);
//
//
//    }
}
