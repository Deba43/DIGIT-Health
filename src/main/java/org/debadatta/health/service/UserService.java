package org.debadatta.health.service;

import java.util.Optional;

import org.debadatta.health.dto.AdminDto;
import org.debadatta.health.dto.DoctorsDto;
import org.debadatta.health.dto.PatientsDto;
import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.model.User;

public interface UserService {

    void saveUser(User user);

    void saveAdmin(AdminDto adminDTO);

    void saveDoctor(DoctorsDto doctorDTO);

    void savePatient(PatientsDto patientDTO);

    Optional<User> findByEmail(String email);

    Optional<Admin> findAdminById(Long id);

    Optional<Doctors> findDoctorById(Long id);

    Optional<Patients> findPatientById(Long id);

    void saveIfNotExists(String email, String password, String role);

}
