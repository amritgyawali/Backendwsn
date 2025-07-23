package com.wsn.controller;

import com.wsn.model.dto.CustomerProfileRequest;
import com.wsn.model.dto.ProfileUpdateRequest;
import com.wsn.model.dto.VendorProfileRequest;
import com.wsn.model.entity.User;
import com.wsn.security.UserDetailsImpl;
import com.wsn.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public Object getCurrentUserProfile(){
        return profileService.getCurrentUserProfile();
    }

    @PutMapping
    public Object updateProfile(@RequestBody ProfileUpdateRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return profileService.updateUserProfile(userDetails.user.getId(), request);
    }

    @PostMapping("/customer")
    public Object createCustomerProfile(@RequestBody CustomerProfileRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        if (userDetails.user.getRole() != User.Role.CUSTOMER){
            throw new RuntimeException("User is not a customer");
        }
        return profileService.createCustomerProfile(userDetails.user.getId(), request);
    }

    @PostMapping("/vendor")
    public Object createVendorProfile(@RequestBody VendorProfileRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        if (userDetails.user.getRole() != User.Role.VENDOR){
            throw new RuntimeException("User is not a customer");
        }
        return profileService.createVendorProfile(userDetails.user.getId(), request);
    }
}
