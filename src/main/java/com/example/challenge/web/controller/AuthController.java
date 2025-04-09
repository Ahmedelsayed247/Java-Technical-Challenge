package com.example.challenge.web.controller;

import com.example.challenge.domain.model.User;
import com.example.challenge.domain.repository.UserRepository;
import com.example.challenge.domain.service.UserService;
import com.example.challenge.infrastructure.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtUtils;
    @PostMapping("/login")
    public String authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }
        userService.save(user);
        return "User registered successfully!";
    }
}
