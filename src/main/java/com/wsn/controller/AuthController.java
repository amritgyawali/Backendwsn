package com.wsn.controller;

import com.wsn.config.JwtUtils;
import com.wsn.model.dto.LoginRequest;
import com.wsn.model.dto.LoginResponse;
import com.wsn.model.dto.RegisterRequest;
import com.wsn.model.entity.User;
import com.wsn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody com.wsn.model.dto.LoginRequest request){
        Authentication authentication = authService.authenticateUser(
                request.getEmail(),
                request.getPassword()
        );

        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody com.wsn.model.dto.RegisterRequest request){
        User user = authService.registerUser(
                request.getEmail(),
                request.getPassword(),
                User.Role.valueOf(request.getRole())
        );
        return ResponseEntity.ok("User registered successfully");
    }

}
