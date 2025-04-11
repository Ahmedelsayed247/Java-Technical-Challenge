package com.example.challenge.application.service;
import com.example.challenge.application.exception.UserExceptionHandling.InvalidCredentialsException;
import com.example.challenge.application.exception.UserExceptionHandling.PasswordMismatchException;
import com.example.challenge.application.exception.UserExceptionHandling.UserAlreadyExistsException;
import com.example.challenge.domain.model.User;
import com.example.challenge.infrastructure.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService ;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<?> authenticateUser(String username, String password) {
        try {
            // Attempt to authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // If successful, generate a JWT token for the user
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(jwtTokenProvider.generateToken(userDetails.getUsername()));

        } catch (Exception e) {
            // Throw an exception with a meaningful message when authentication fails
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    public String registerUser(String username, String password, String confirmPassword) {
        // Check if the passwords match
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Passwords do not match.");
        }

        // Check if the user already exists and throw an exception if necessary
        if (userService.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with this username already exists.");
        }

        // Proceed with user registration logic
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);

        // Return a success response with a message
        return "User registered successfully!";
    }

}
