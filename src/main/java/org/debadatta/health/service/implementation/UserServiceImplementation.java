package org.debadatta.health.service.implementation;

import org.debadatta.health.dto.AdminDto;
import org.debadatta.health.dto.DoctorsDto;
import org.debadatta.health.dto.PatientsDto;
import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.model.User;
import org.debadatta.health.repo.UserRepo;
import org.debadatta.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UserServiceImplementation(DynamoDBMapper dynamoDBMapper, PasswordEncoder passwordEncoder) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        dynamoDBMapper.save(user);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String role = user instanceof Admin ? "ADMIN" : (user instanceof Doctors ? "DOCTOR" : "PATIENT");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }

    @Override

    public void saveAdmin(AdminDto adminDTO) {
        try {
            Admin admin = new Admin();
            admin.setName(adminDTO.getName());
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
            admin.setRole(adminDTO.getRole());
            admin.setEmail(adminDTO.getEmail());
            admin.setPhone_no(adminDTO.getPhone_no());
            admin.setAge(adminDTO.getAge());
            dynamoDBMapper.save(admin);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not create Admin: " + e.getMessage());
        }
    }

    @Override

    public void saveDoctor(DoctorsDto doctorDTO) {
        try {
            Doctors doc = new Doctors();
            doc.setName(doctorDTO.getName());
            doc.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
            doc.setEmail(doctorDTO.getEmail());
            doc.setPhone_no(doctorDTO.getPhone_no());
            doc.setAge(doctorDTO.getAge());
            doc.setSpecialization(doctorDTO.getSpecialization());
            doc.setExperience(doctorDTO.getExperience());
            doc.setAvailability(doctorDTO.getAvailability());

            dynamoDBMapper.save(doc);
        } catch (Exception e) {
            throw new RuntimeException("Could not create Doctor: " + e.getMessage());
        }
    }

    @Override

    public void savePatient(PatientsDto patientDTO) {
        try {
            Patients pat = new Patients();
            pat.setName(patientDTO.getName());
            pat.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
            pat.setAge(patientDTO.getAge());
            pat.setDisease(patientDTO.getDisease());
            pat.setGender(patientDTO.getGender());
            pat.setAddress(patientDTO.getAddress());
            pat.setPhone_no(patientDTO.getPhone_no());
            pat.setEmail(patientDTO.getEmail());
            dynamoDBMapper.save(pat);
        } catch (Exception e) {
            throw new RuntimeException("Could not create patient: " + e.getMessage());
        }
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<Admin> findAdminById(Long id) {
        return userRepo.findAdminById(id);

    }

    @Override
    public Optional<Doctors> findDoctorById(Long id) {
        return userRepo.findDoctorById(id);
    }

    @Override
    public Optional<Patients> findPatientById(Long id) {
        return userRepo.findPatientById(id);

    }

}