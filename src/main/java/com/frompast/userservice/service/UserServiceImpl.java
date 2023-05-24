package com.frompast.userservice.service;

import com.frompast.userservice.exceptions.AuthenticationException;
import com.frompast.userservice.exceptions.UserExistException;
import com.frompast.userservice.exceptions.UserNotFoundException;
import com.frompast.userservice.model.Usr;
import com.frompast.userservice.model.UserProfile;
import com.frompast.userservice.repository.UserProfileRepository;
import com.frompast.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;

    @Override
    public Usr registerUser(Usr user) {
        Optional<Usr> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserExistException("User with email " + user.getEmail() + " already exists.");
        }
        UserProfile userProfile = new UserProfile();
        Usr usr = userRepository.save(user);
        userProfile.setId(usr.getId());
        userProfileRepository.save(userProfile);
        return usr;
    }

    @Override
    public Usr login(String email, String password) {
        Optional<Usr> userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail.isPresent() && userByEmail.get().getPassword().equals(password)) {
            return userByEmail.get();
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public void updateUserProfile(Long userId, UserProfile userProfile) {
        Optional<UserProfile> userById = userProfileRepository.findById(userId);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not exists");
        }
        UserProfile userForUpdate = userById.get();
        userForUpdate.setDescription(userProfile.getDescription());
        userForUpdate.setPhoto(userProfile.getPhoto());
        userForUpdate.setBirthDate(userProfile.getBirthDate());
        userProfileRepository.save(userForUpdate);
    }
}
