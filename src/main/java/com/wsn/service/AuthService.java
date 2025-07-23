package com.wsn.service;

import com.wsn.model.dto.CustomerProfileRequest;
import com.wsn.model.dto.CustomerProfileResponse;
import com.wsn.model.dto.ProfileUpdateRequest;
import com.wsn.model.dto.VendorProfileRequest;
import com.wsn.model.entity.User;
import com.wsn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProfileService profileService;


    public User registerUser(String email,String password, User.Role role){
        if (userRepository.existsByEmail(email)){
            throw new RuntimeException("Email already exist");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);

        profileService.updateUserProfile(user.getId(),
            new ProfileUpdateRequest(
                    email.split("@")[0],
                    null,
                    null
            ));

    switch (role){
        case CUSTOMER:
            profileService.createCustomerProfile(user.getId(),
            new CustomerProfileRequest(
            ));
            break;
        case VENDOR:
            profileService.createVendorProfile(user.getId(),
                    new VendorProfileRequest(
                            "My Business",
                            "Service Type"
                    ));
            break;
        case ADMIN:
            profileService.createAdminProfile(user.getId());
            break;
    }
    return user;
    }

    public Authentication authenticateUser(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
