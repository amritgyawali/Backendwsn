package com.wsn.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role{
        CUSTOMER, VENDOR, ADMIN
    }
}
