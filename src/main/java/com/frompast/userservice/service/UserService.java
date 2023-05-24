package com.frompast.userservice.service;

import com.frompast.userservice.model.Usr;
import com.frompast.userservice.model.UserProfile;

public interface UserService {
    Usr registerUser(Usr user);
    Usr login(String email, String password);
    void updateUserProfile(Long userId, UserProfile userProfile);
}
