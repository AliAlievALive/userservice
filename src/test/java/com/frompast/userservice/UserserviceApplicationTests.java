package com.frompast.userservice;

import com.frompast.userservice.exceptions.AuthenticationException;
import com.frompast.userservice.exceptions.UserExistException;
import com.frompast.userservice.model.Usr;
import com.frompast.userservice.repository.UserProfileRepository;
import com.frompast.userservice.repository.UserRepository;
import com.frompast.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserserviceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userProfileRepository);
    }

    @Test
    void registerUser_WithNonExistingEmail_ShouldReturnRegisteredUser() {
        // Arrange
        Usr user = new Usr();
        user.setEmail("test@example.com");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        Usr registeredUser = userService.registerUser(user);

        // Assert
        assertEquals(user, registeredUser);
        verify(userRepository).findUserByEmail(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void registerUser_WithExistingEmail_ShouldThrowUserExistException() {
        // Arrange
        Usr user = new Usr();
        user.setEmail("test@example.com");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UserExistException.class, () -> userService.registerUser(user));
        verify(userRepository).findUserByEmail(user.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_WithValidCredentials_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        Usr user = new Usr();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Usr loggedInUser = userService.login(email, password);

        // Assert
        assertEquals(user, loggedInUser);
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void login_WithInvalidCredentials_ShouldThrowAuthenticationException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AuthenticationException.class, () -> userService.login(email, password));
        verify(userRepository).findUserByEmail(email);
    }

}
