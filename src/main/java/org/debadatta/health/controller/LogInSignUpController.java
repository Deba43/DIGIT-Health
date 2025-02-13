package org.debadatta.health.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class LogInSignUpController {

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Health System API!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        return ResponseEntity.ok("User logged in successfully as " + authentication.getAuthorities());
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin() {
        return handleSignup("ROLE_ADMIN");
    }

    @PostMapping("/signup/doctor")
    public ResponseEntity<?> registerDoctor() {
        return handleSignup("ROLE_DOCTOR");
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient() {
        return handleSignup("ROLE_PATIENT");
    }

    private ResponseEntity<?> handleSignup(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User is already logged in as " + authentication.getAuthorities());
        }

        return ResponseEntity.ok(role + " registration API endpoint");
    }
}
