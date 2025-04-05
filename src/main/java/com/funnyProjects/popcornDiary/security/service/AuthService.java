/*
 * Created by minmin_tranova on 04.04.2025
 */

package com.funnyProjects.popcornDiary.security.service;

import com.funnyProjects.popcornDiary.exception.InvalidCredentialsException;
import com.funnyProjects.popcornDiary.exception.UserAlreadyExistsException;
import com.funnyProjects.popcornDiary.exception.UserNotFoundException;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import com.funnyProjects.popcornDiary.security.model.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(User userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        System.out.println("Generated token: " + token); // Debug log
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " does not exist"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
