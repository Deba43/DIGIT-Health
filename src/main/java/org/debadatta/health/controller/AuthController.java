package org.debadatta.health.controller;

import org.debadatta.health.service.AdminService;
import org.debadatta.health.service.DoctorsService;
import org.debadatta.health.service.PatientsService;
import org.debadatta.health.service.UserService;

import java.util.Optional;

import org.debadatta.health.dto.AdminDto;
import org.debadatta.health.dto.DoctorsDto;
import org.debadatta.health.dto.PatientsDto;
import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final AdminService adminService;
    @Autowired
    private final DoctorsService doctorService;
    @Autowired
    private final PatientsService patientService;
    @Autowired
    PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminDto adminDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            userService.saveAdmin(adminDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/signup/doctor")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody DoctorsDto doctorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            userService.saveDoctor(doctorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Doctor registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientsDto patientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            userService.savePatient(patientDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        Optional<Admin> adminOptional = adminService.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (!passwordEncoder.matches(password, admin.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            userService.saveIfNotExists(admin.getEmail(), admin.getPassword(), "ADMIN");
            return authenticateUser(admin.getEmail(), password, "ADMIN");
        }

        Optional<Doctors> doctorOptional = doctorService.findByEmail(email);
        if (doctorOptional.isPresent()) {
            Doctors doctor = doctorOptional.get();
            if (!passwordEncoder.matches(password, doctor.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            userService.saveIfNotExists(doctor.getEmail(), doctor.getPassword(), "DOCTOR");
            return authenticateUser(doctor.getEmail(), password, "DOCTOR");
        }

        Optional<Patients> patientOptional = patientService.findByEmail(email);
        if (patientOptional.isPresent()) {
            Patients patient = patientOptional.get();
            if (!passwordEncoder.matches(password, patient.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            userService.saveIfNotExists(patient.getEmail(), patient.getPassword(), "PATIENT");
            return authenticateUser(patient.getEmail(), password, "PATIENT");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found with email: " + email);
    }

    private ResponseEntity<?> authenticateUser(String email, String password, String role) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("User logged in successfully as " + role);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

}
