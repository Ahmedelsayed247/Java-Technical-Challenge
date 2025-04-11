package com.example.challenge.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data

public class RegisterDTO {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
private String password;
    @NotNull(message = "confirm password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
private String confirmPassword;
}
