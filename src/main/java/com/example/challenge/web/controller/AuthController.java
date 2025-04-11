package com.example.challenge.web.controller;
import com.example.challenge.web.dto.LoginDTO;
import com.example.challenge.web.dto.RegisterDTO;
import com.example.challenge.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        String token = authService.authenticateUser(loginDTO.getUsername(), loginDTO.getPassword()).getBody().toString();
        // Return success response with token
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDto) {
        // Call the service to register the user
        String message = authService.registerUser(registerDto.getUsername(), registerDto.getPassword(), registerDto.getConfirmPassword());
        // Return success response
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }

}
