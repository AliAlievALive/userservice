package com.frompast.userservice.service;

import com.frompast.userservice.exceptions.UserNotFoundException;
import com.frompast.userservice.model.UserProfile;
import com.frompast.userservice.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile getUserProfile(Long userId) {
        Optional<UserProfile> userProfileById = userProfileRepository.findById(userId);
        if (userProfileById.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not exists");
        }
        return userProfileById.get();
    }
}
