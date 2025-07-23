package com.wsn.model.dto;

import com.wsn.model.entity.Customer;
import com.wsn.model.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CustomerProfileResponse {
    private UserProfile profile;
    private Customer customer;

    public CustomerProfileResponse(UserProfile profile, Customer customer) {
        this.profile = profile;
        this.customer = customer;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
