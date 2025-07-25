package com.wsn.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;


    private LocalDateTime weddingDate;

    @Column(nullable = false)
    private double budget;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private Long version;

    public Customer(){};

    public Customer(UUID id, User user, LocalDateTime weddingDate, double budget, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.weddingDate = weddingDate;
        this.budget = budget;
        this.createdAt = createdAt;
    }

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

    public LocalDateTime getWeddingDate() {
        return LocalDateTime.from(weddingDate);
    }

    public void setWeddingDate(LocalDateTime weddingDate) {
        this.weddingDate = weddingDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
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
