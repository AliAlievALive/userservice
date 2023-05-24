package com.frompast.userservice.service;

import com.frompast.userservice.model.UserProfile;

public interface UserProfileService {
    UserProfile getUserProfile(Long userId);
}
