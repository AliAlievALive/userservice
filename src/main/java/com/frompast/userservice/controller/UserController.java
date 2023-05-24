package com.frompast.userservice.controller;

import com.frompast.userservice.model.UserProfile;
import com.frompast.userservice.model.Usr;
import com.frompast.userservice.service.UserProfileService;
import com.frompast.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private UserProfileService userProfileService;

    @PostMapping("/register")
    public ResponseEntity<Usr> registerUser(@RequestBody Usr user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Usr> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userService.login(email, password));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
        UserProfile userProfile = userProfileService.getUserProfile(userId);
        userProfile.add(linkTo(
                methodOn(UserController.class).getUserProfile(userId))
                .withSelfRel());
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfile userProfile) {
        userService.updateUserProfile(userId, userProfile);
        return ResponseEntity.ok().build();
    }
}
