package com.wsn.controller;

import com.wsn.model.dto.InquiryRequest;
import com.wsn.model.entity.Inquiry;
import com.wsn.security.UserDetailsImpl;
import com.wsn.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<Inquiry> createInquiry(@RequestBody InquiryRequest request){
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Inquiry inquiry = inquiryService.createInquiry(user.getId(),request);
        return ResponseEntity.ok(inquiry);
    }

    @PutMapping("/{id}/respond")
    public ResponseEntity<Inquiry> respondToInquiry(
            @PathVariable UUID id,
            @RequestBody String response) {
        Inquiry inquiry = inquiryService.respondToInquiry(id, response);
        return ResponseEntity.ok(inquiry);
    }

    @PutMapping("/{id}/convert")
    public ResponseEntity<Inquiry> convertToBooking(@PathVariable UUID id) {
        Inquiry inquiry = inquiryService.convertToBooking(id);
        return ResponseEntity.ok(inquiry);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Inquiry>> getCustomerInquiries() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return ResponseEntity.ok(inquiryService.getCustomerInquiries(user.getId()));
    }

    @GetMapping("/vendor")
    public ResponseEntity<List<Inquiry>> getVendorInquiries() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return ResponseEntity.ok(inquiryService.getVendorInquiries(user.getId()));
    }
}
