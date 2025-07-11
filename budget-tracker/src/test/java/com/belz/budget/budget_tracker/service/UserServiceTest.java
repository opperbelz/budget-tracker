package com.belz.budget.budget_tracker.service;

import com.belz.budget.budget_tracker.domain.User;
import com.belz.budget.budget_tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerNewUser_whenUsernameIsUnique_shouldSaveAndReturnUser() {
        String username = "newUser";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        User userToSave = new User();
        userToSave.setUsername(username);
        userToSave.setPassword(encodedPassword);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(userToSave);
        User savedUser = userService.registerNewUser(username, rawPassword);
        assertNotNull(savedUser);
        assertEquals(username, savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerNewUser_whenUsernameExists_shouldThrowException() {
        String existingUsername = "existingUser";
        String rawPassword = "password123";
        when(userRepository.findByUsername(existingUsername)).thenReturn(Optional.of(new User()));
        assertThrows(IllegalStateException.class, () -> {
            userService.registerNewUser(existingUsername, rawPassword);
        });
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}