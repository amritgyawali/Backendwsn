package com.wsn.model.dto;

import com.wsn.model.entity.UserProfile;
import com.wsn.model.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VendorProfileResponse {
    private UserProfile profile;
    private Vendor vendor;

    public VendorProfileResponse(UserProfile profile, Vendor vendor) {
        this.profile = profile;
        this.vendor = vendor;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
