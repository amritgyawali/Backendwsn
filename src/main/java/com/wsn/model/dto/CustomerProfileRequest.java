package com.wsn.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerProfileRequest {
    private LocalDateTime weddingDate;
    private double budget;

    public CustomerProfileRequest(LocalDateTime weddingDate, double budget) {
        this.weddingDate = weddingDate;
        this.budget = budget;
    }

    public CustomerProfileRequest(){}

    public LocalDateTime getWeddingDate() {
        return weddingDate;
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
}
