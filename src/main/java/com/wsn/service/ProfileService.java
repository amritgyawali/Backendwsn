package com.wsn.service;

import com.wsn.model.dto.AdminProfileResponse;
import com.wsn.model.dto.CustomerProfileResponse;
import com.wsn.model.dto.CustomerProfileRequest;
import com.wsn.model.dto.ProfileUpdateRequest;
import com.wsn.model.dto.VendorProfileRequest;
import com.wsn.model.dto.VendorProfileResponse;
import com.wsn.model.entity.*;
import com.wsn.repository.*;
import com.wsn.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.wsn.model.entity.User.Role.*;

@Service
public class ProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    public UserProfile getUserProfile(UUID userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Transactional
    public UserProfile updateUserProfile(UUID userId, ProfileUpdateRequest request) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseGet(() -> {
                    UserProfile newProfile = new UserProfile();
                    // Fetch the User entity and set it (required for @MapsId)
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    newProfile.setUser(user);
                    return newProfile;
                });

        // Only update fields that are not null
        if (request.getFullName() != null) {
            profile.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            profile.setPhone(request.getPhone());
        }
        if (request.getAvatarUrl() != null) {
            profile.setAvatarUrl(request.getAvatarUrl());
        }

        return userProfileRepository.save(profile);
    }

    @Transactional
    public Customer createCustomerProfile(UUID userId, CustomerProfileRequest request) {
        Customer customer = new Customer();
        // Fetch the User entity and set it (required for @MapsId)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        customer.setUser(user);
        customer.setWeddingDate(request.getWeddingDate());
        customer.setBudget(request.getBudget());
        return customerRepository.save(customer);
    }

    @Transactional
    public Vendor createVendorProfile(UUID userId, VendorProfileRequest request) {
        Vendor vendor = new Vendor();
        // Fetch the User entity and set it (required for @MapsId)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        vendor.setUser(user);
        vendor.setBusinessName(request.getBusinessName());
        vendor.setServiceType(request.getServiceType());
        return vendorRepository.save(vendor);
    }

    @Transactional
    public Admin createAdminProfile(UUID userId) {
        Admin admin = new Admin();
        // Fetch the User entity and set it (required for @MapsId)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        admin.setUser(user);
        admin.setDepartment("Platform Management");
        return adminRepository.save(admin);
    }

    public Object getCurrentUserProfile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        UUID userId = userDetails.getId();
        User.Role role = userDetails.user.getRole();

        UserProfile profile = getUserProfile(userId);

        switch (role) {
            case CUSTOMER:
                Customer customer = customerRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Customer profile not found"));
                return new CustomerProfileResponse(profile, customer);
            case VENDOR:
                Vendor vendor = vendorRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Vendor profile not found"));
                return new VendorProfileResponse(profile, vendor);
            case ADMIN:
                Admin admin = adminRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Admin profile not found"));
                return new AdminProfileResponse(profile, admin);
            default:
                throw new RuntimeException("Invalid user role");
        }
    }
}