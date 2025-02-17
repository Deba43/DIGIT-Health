package org.debadatta.health.controller;

import lombok.extern.slf4j.Slf4j;
import org.debadatta.health.dto.AdminDto;
import org.debadatta.health.dto.DoctorsDto;
import org.debadatta.health.dto.PatientsDto;
import org.debadatta.health.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class LogInSignUpController {

    private final UserService userService;

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
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminDto adminDto, BindingResult bindingResult) {
        return handleSignup(adminDto, bindingResult, "ROLE_ADMIN");
    }

    @PostMapping("/signup/doctor")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody DoctorsDto doctorDto, BindingResult bindingResult) {
        return handleSignup(doctorDto, bindingResult, "ROLE_DOCTOR");
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientsDto patientDto, BindingResult bindingResult) {
        return handleSignup(patientDto, bindingResult, "ROLE_PATIENT");
    }

    private ResponseEntity<?> handleSignup(Object dto, BindingResult bindingResult, String role) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            if (dto instanceof AdminDto) userService.saveAdmin((AdminDto) dto);
            else if (dto instanceof DoctorsDto) userService.saveDoctor((DoctorsDto) dto);
            else if (dto instanceof PatientsDto) userService.savePatient((PatientsDto) dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(role + " registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
