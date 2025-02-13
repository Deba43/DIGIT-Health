package org.debadatta.health.repo;

import java.util.Optional;

import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);

	Optional<Admin> findAdminById(Long id);

	Optional<Doctors> findDoctorById(Long id);

	Optional<Patients> findPatientById(Long id);

}