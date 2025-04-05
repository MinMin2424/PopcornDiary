/*
 * Created by minmin_tranova on 04.04.2025
 */

package com.funnyProjects.popcornDiary.controller;

import com.funnyProjects.popcornDiary.dto.LoginDto;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.security.model.AuthenticationResponse;
import com.funnyProjects.popcornDiary.security.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto.getEmail(), loginDto.getPassword()));
    }
}
