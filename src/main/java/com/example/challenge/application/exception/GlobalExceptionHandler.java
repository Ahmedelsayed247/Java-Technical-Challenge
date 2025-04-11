package com.example.challenge.application.exception;
import com.example.challenge.application.exception.ContactExceptionHandling.ContactNotFoundException;
import com.example.challenge.application.exception.ContactExceptionHandling.DuplicateContactException;
import com.example.challenge.application.exception.ContactExceptionHandling.UserNotFoundException;
import com.example.challenge.application.exception.UserExceptionHandling.InvalidCredentialsException;
import com.example.challenge.application.exception.UserExceptionHandling.PasswordMismatchException;
import com.example.challenge.application.exception.UserExceptionHandling.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    //This use for Handle invalid username or password in login
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_CREDENTIOALS",ex.getMessage()) ;
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    //This use for Handle user already exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USER_ALREADY_EXIST",ex.getMessage()) ;
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    //This use for Handle password mismatch when user registers
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse("PASSWORD_MISMATCH", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    // Contact Exceptions
    //This is uses for handling not found contact
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContactNotFound(ContactNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("CONTACT_NOT_FOUND", ex.getMessage()));
    }
    //This is uses for handling not found user when we search for a user to assign contact to him (optional)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("USER_NOT_FOUND", ex.getMessage()));
    }

    //This is uses for handling duplication in contact based on Email
    @ExceptionHandler(DuplicateContactException.class)
    public ResponseEntity<Object> handleDuplicateContact(DuplicateContactException ex) {
        // Return custom error response
        ErrorResponse errorResponse = new ErrorResponse("DUPLICATE_CONTACT", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //This is uses To handle validation in username, password and email
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Collect all validation error messages
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Return errors in the response body with a BAD_REQUEST (400) status
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
