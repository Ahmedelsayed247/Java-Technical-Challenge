package com.example.challenge.web.controller;


import com.example.challenge.application.dto.UserDTO;
import com.example.challenge.application.exception.ErrorResponse;
import com.example.challenge.domain.service.AuthService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDto) {
        String token = authService.authenticateUser(userDto.getUsername(), userDto.getPassword()).getBody().toString();

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("PASSWORD_MISMATCH", "Passwords do not match."));
        }

        String message = authService.registerUser(userDto.getUsername(), userDto.getPassword());

        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }

}
