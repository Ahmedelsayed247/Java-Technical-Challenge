package com.example.challenge.domain.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link User} field-level Bean Validation constraints.
 *
 * PR change: @Size constraints were removed from the {@code username} and
 * {@code password} fields. Only @NotNull remains at the field level.
 * These tests verify the new constraint set on those fields.
 */
class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private User buildUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    private Set<ConstraintViolation<User>> violationsFor(String username, String password) {
        return validator.validate(buildUser(username, password));
    }

    // -------------------------------------------------------------------------
    // @NotNull on username (still present after PR)
    // -------------------------------------------------------------------------

    @Test
    void username_null_shouldFailNotNullConstraint() {
        Set<ConstraintViolation<User>> violations = violationsFor(null, "anypassword");

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")
                        && v.getMessage().equals("Username cannot be null"));
    }

    // -------------------------------------------------------------------------
    // @NotNull on password (still present after PR)
    // -------------------------------------------------------------------------

    @Test
    void password_null_shouldFailNotNullConstraint() {
        Set<ConstraintViolation<User>> violations = violationsFor("validuser", null);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")
                        && v.getMessage().equals("Password cannot be null"));
    }

    @Test
    void username_and_password_null_shouldFailBothNotNullConstraints() {
        Set<ConstraintViolation<User>> violations = violationsFor(null, null);

        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString())
                .containsExactlyInAnyOrder("username", "password");
    }

    // -------------------------------------------------------------------------
    // Username length — @Size removed from field, so short/long usernames are valid
    // -------------------------------------------------------------------------

    @Test
    void username_oneChar_shouldPassFieldValidation() {
        // Previously @Size(min=3) would reject this; field constraint removed in PR
        Set<ConstraintViolation<User>> violations = violationsFor("a", "anypassword");

        assertThat(violations)
                .as("Single-character username must pass now that @Size is removed from the field")
                .isEmpty();
    }

    @Test
    void username_twoChars_shouldPassFieldValidation() {
        // Previously @Size(min=3) would reject this; field constraint removed in PR
        Set<ConstraintViolation<User>> violations = violationsFor("ab", "anypassword");

        assertThat(violations)
                .as("Two-character username must pass now that @Size is removed from the field")
                .isEmpty();
    }

    @Test
    void username_emptyString_shouldPassFieldValidation() {
        // @NotNull allows empty strings; @Size(min=3) used to block this; field constraint removed in PR
        Set<ConstraintViolation<User>> violations = violationsFor("", "anypassword");

        assertThat(violations)
                .as("Empty string username must pass now that @Size is removed from the field")
                .isEmpty();
    }

    @Test
    void username_51Chars_shouldPassFieldValidation() {
        // Previously @Size(max=50) would reject this; field constraint removed in PR
        String longUsername = "a".repeat(51);
        Set<ConstraintViolation<User>> violations = violationsFor(longUsername, "anypassword");

        assertThat(violations)
                .as("51-character username must pass now that @Size is removed from the field")
                .isEmpty();
    }

    @Test
    void username_100Chars_shouldPassFieldValidation() {
        // Further boundary check; previously blocked by @Size(max=50)
        String longUsername = "u".repeat(100);
        Set<ConstraintViolation<User>> violations = violationsFor(longUsername, "anypassword");

        assertThat(violations)
                .as("100-character username must pass now that @Size is removed from the field")
                .isEmpty();
    }

    // -------------------------------------------------------------------------
    // Password length — @Size removed from field, so short passwords are valid
    // -------------------------------------------------------------------------

    @Test
    void password_oneChar_shouldPassFieldValidation() {
        // Previously @Size(min=8) would reject this; field constraint removed in PR
        Set<ConstraintViolation<User>> violations = violationsFor("validuser", "x");

        assertThat(violations)
                .as("Single-character password must pass now that @Size is removed from the field")
                .isEmpty();
    }

    @Test
    void password_sevenChars_shouldPassFieldValidation() {
        // Previously @Size(min=8) would reject this (boundary: one below old minimum)
        Set<ConstraintViolation<User>> violations = violationsFor("validuser", "short12");

        assertThat(violations)
                .as("Seven-character password must pass now that @Size is removed from the field")
                .isEmpty();
    }

    @Test
    void password_emptyString_shouldPassFieldValidation() {
        // @NotNull allows empty strings; @Size(min=8) used to block this; field constraint removed in PR
        Set<ConstraintViolation<User>> violations = violationsFor("validuser", "");

        assertThat(violations)
                .as("Empty string password must pass now that @Size is removed from the field")
                .isEmpty();
    }

    // -------------------------------------------------------------------------
    // Happy-path: valid user with common values
    // -------------------------------------------------------------------------

    @Test
    void validUser_shouldHaveNoConstraintViolations() {
        Set<ConstraintViolation<User>> violations = violationsFor("john_doe", "securepass");

        assertThat(violations).isEmpty();
    }

    @Test
    void validUser_constructorWithIdUsernamePassword_shouldHaveNoConstraintViolations() {
        User user = new User(1L, "alice", "password123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    // -------------------------------------------------------------------------
    // Regression: ensure no unexpected additional field constraints were introduced
    // -------------------------------------------------------------------------

    @Test
    void validUser_usernameExactlyThreeChars_shouldHaveNoConstraintViolations() {
        // Boundary that was the previous minimum; still valid (and now even shorter lengths are too)
        Set<ConstraintViolation<User>> violations = violationsFor("abc", "mypassword");

        assertThat(violations).isEmpty();
    }

    @Test
    void validUser_usernameExactly50Chars_shouldHaveNoConstraintViolations() {
        // Boundary that was the previous maximum; still valid
        String fiftyCharUsername = "u".repeat(50);
        Set<ConstraintViolation<User>> violations = violationsFor(fiftyCharUsername, "mypassword");

        assertThat(violations).isEmpty();
    }

    @Test
    void validUser_passwordExactlyEightChars_shouldHaveNoConstraintViolations() {
        // Boundary that was the previous minimum; still valid
        Set<ConstraintViolation<User>> violations = violationsFor("validuser", "12345678");

        assertThat(violations).isEmpty();
    }
}