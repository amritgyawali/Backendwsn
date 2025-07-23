package com.wsn.service;

import com.wsn.model.dto.*;
import com.wsn.model.entity.User;
import com.wsn.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Service
public class DemoAccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileService profileService;

    @PostConstruct
    public void initDemoAccounts() {
        createDemoUser("customer@demo.com", "DemoCustomer123", User.Role.CUSTOMER);
        createDemoUser("vendor@demo.com", "DemoVendor123", User.Role.VENDOR);
        createDemoUser("admin@demo.com", "DemoAdmin123", User.Role.ADMIN);
    }

    @Transactional
    private void createDemoUser(String email, String password, User.Role role) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userRepository.save(user);

            // Create profile
            profileService.updateUserProfile(user.getId(),
                    new ProfileUpdateRequest(
                            "Demo " + role.name(),
                            "1234567890",
                            null
                    )
            );

            // Create role-specific profile
            switch (role) {
                case CUSTOMER:
                    profileService.createCustomerProfile(user.getId(),
                            new CustomerProfileRequest(
                                    LocalDateTime.now().plusDays(317),
                                    500000.00
                            )
                    );
                    break;
                case VENDOR:
                    profileService.createVendorProfile(user.getId(),
                            new VendorProfileRequest(
                                    "Demo Vendor Business",
                                    "Photography"
                            )
                    );
                    break;
                case ADMIN:
                    profileService.createAdminProfile(user.getId());
                    break;
            }
        }
    }
}