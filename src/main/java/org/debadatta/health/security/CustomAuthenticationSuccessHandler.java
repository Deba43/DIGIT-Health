package org.debadatta.health.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        Long id = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username))
                .getId();

        Map<String, Object> userDetails = new HashMap<>();

        if ("ROLE_ADMIN".equals(role) && id != null) {
            Optional<Admin> userObj = userService.findAdminById(id);
            userObj.ifPresent(user -> {
                userDetails.put("userId", user.getId());
                userDetails.put("name", user.getName());
                userDetails.put("email", user.getEmail());
                userDetails.put("phone", user.getPhone_no());
                userDetails.put("role", user.getRole());
                userDetails.put("age", user.getAge());

            });
        } else if ("ROLE_DOCTOR".equals(role) && id != null) {
            Optional<Doctors> userObj = userService.findDoctorById(id);
            userObj.ifPresent(user -> {
                userDetails.put("userId", user.getId());
                userDetails.put("name", user.getName());
                userDetails.put("email", user.getEmail());
                userDetails.put("phone", user.getPhone_no());
                userDetails.put("age", user.getAge());
                userDetails.put("specialization", user.getSpecialization());
                userDetails.put("experience", user.getExperience());
                userDetails.put("availability", user.getAvailability());

            });
        } else if ("ROLE_PATIENT".equals(role) && id != null) {
            Optional<Patients> userObj = userService.findPatientById(id);
            userObj.ifPresent(user -> {
                userDetails.put("userId", user.getId());
                userDetails.put("name", user.getName());
                userDetails.put("email", user.getEmail());
                userDetails.put("phone", user.getPhoneNumber());
                userDetails.put("gender", user.getGender());
                userDetails.put("disease", user.getDisease());
                userDetails.put("address", user.getAddress());

            });
        }

        userDetails.put("role", role);
        userDetails.put("message", "User logged in successfully");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(userDetails));
    }
}
