package com.wsn.model.dto;

import com.wsn.model.entity.Admin;
import com.wsn.model.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminProfileResponse {
    UserProfile profile;
    Admin admin;

    public AdminProfileResponse(UserProfile profile, Admin admin) {
        this.profile = profile;
        this.admin = admin;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
