package com.wsn.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "vendors")
public class Vendor {
    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "business_name")
    private String businessName;
    
    @Column(name = "service_type")
    private String serviceType;
    
    @Column(columnDefinition = "float8 default 4.5")
    private final double rating = 4.5;
    
    @Column(columnDefinition = "boolean default true")
    private final boolean active = true;
    
    @Column(name = "response_rate", columnDefinition = "float8 default 80.0")
    private double responseRate = 80.0;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private Long version;

    public Vendor(UUID id, User user, String businessName, String serviceType, LocalDateTime createdAt, Long version) {
        this.id = id;
        this.user = user;
        this.businessName = businessName;
        this.serviceType = serviceType;
        this.createdAt = createdAt;
        this.version = version;
        this.responseRate = 80.0; 
    }

    public Vendor(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getRating() {
        return rating;
    }

    public boolean isActive() {
        return active;
    }

    public double getResponseRate() {
        return responseRate;
    }

    public void setResponseRate(double responseRate) {
        this.responseRate = responseRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
