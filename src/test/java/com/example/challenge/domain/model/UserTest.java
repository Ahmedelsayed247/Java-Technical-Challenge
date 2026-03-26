package com.example.challenge.domain.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // --- Tests verifying @Size is NO LONGER enforced on username field ---

    @Test
    void username_shorterThanThreeChars_shouldPassFieldValidation() {
        // Previously would fail @Size(min=3), now the field has no @Size constraint
        User user = new User(1L, "ab", "somepassword");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty(),
            "username shorter than 3 chars should no longer violate any field constraint");
    }

    @Test
    void username_singleChar_shouldPassFieldValidation() {
        // Boundary: exactly 1 character — previously invalid, now valid at field level
        User user = new User(1L, "a", "somepassword");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty(),
            "single-character username should pass now that @Size is removed from the field");
    }

    @Test
    void username_longerThanFiftyChars_shouldPassFieldValidation() {
        // Previously would fail @Size(max=50), now the field has no @Size constraint
        String longUsername = "a".repeat(51);
        User user = new User(1L, longUsername, "somepassword");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty(),
            "username longer than 50 chars should no longer violate any field constraint");
    }

    @Test
    void username_exactlyOneHundredChars_shouldPassFieldValidation() {
        // Regression: very long username well beyond old max=50 is now allowed
        String longUsername = "u".repeat(100);
        User user = new User(1L, longUsername, "somepassword");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty(),
            "100-character username should pass now that field-level @Size(max=50) is removed");
    }

    @Test
    void username_emptyString_shouldPassFieldValidation() {
        // Empty string is not null, so @NotNull won't trigger; no @Size on field either
        User user = new User(1L, "", "somepassword");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty(),
            "empty string username should pass field validation without @Size constraint");
    }

    // --- Tests verifying @Size is NO LONGER enforced on password field ---

    @Test
    void password_shorterThanEightChars_shouldPassFieldValidation() {
        // Previously would fail @Size(min=8), now the field has no @Size constraint
        User user = new User(1L, "validuser", "short");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty(),
            "password shorter than 8 chars should no longer violate any field constraint");
    }

    @Test
    void password_singleChar_shouldPassFieldValidation() {
        // Boundary: 1-character password — previously invalid, now valid at field level
        User user = new User(1L, "validuser", "x");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty(),
            "single-character password should pass now that @Size is removed from the field");
    }

    @Test
    void password_emptyString_shouldPassFieldValidation() {
        // Empty string is not null; without @Size there is no minimum length constraint
        User user = new User(1L, "validuser", "");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty(),
            "empty string password should pass field validation without @Size constraint");
    }

    @Test
    void password_exactlySevenChars_shouldPassFieldValidation() {
        // Boundary regression: 7 chars was invalid before (min=8); must be valid now
        User user = new User(1L, "validuser", "seven77");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty(),
            "7-character password should pass now that field-level @Size(min=8) is removed");
    }

    // --- Tests verifying @NotNull is STILL enforced (unchanged constraint) ---

    @Test
    void username_null_shouldFailNotNullValidation() {
        User user = new User(1L, null, "somepassword");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty(), "null username should still fail @NotNull validation");
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().equals("Username cannot be null")),
            "violation message should match @NotNull message for username");
    }

    @Test
    void password_null_shouldFailNotNullValidation() {
        User user = new User(1L, "validuser", null);
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty(), "null password should still fail @NotNull validation");
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().equals("Password cannot be null")),
            "violation message should match @NotNull message for password");
    }

    // --- Tests verifying a fully valid user passes all field validations ---

    @Test
    void validUser_withShortUsernameAndShortPassword_shouldPassAllFieldValidations() {
        // A user that would have been rejected under the old @Size rules is now fully valid
        User user = new User(1L, "ab", "pw");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(),
            "User with 2-char username and 2-char password should be valid after @Size removal");
    }

    @Test
    void validUser_withTypicalValues_shouldPassAllFieldValidations() {
        User user = new User(1L, "alice", "correcthorsebatterystaple");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Typical valid user should pass all field validations");
    }
}