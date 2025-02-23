package org.debadatta.health.service;

import java.util.List;
import java.util.Optional;

import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public AdminService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Patients> getAllPatients() {
        return adminRepo.getAllPatients();
    }

    public List<Patients> getPatientsByDisease(String disease) {
        return adminRepo.getPatientsByDisease(disease);
    }

    public Patients getPatientsById(String p_id) {
        return adminRepo.getPatientsById(p_id);
    }

    public List<Doctors> getDoctorsBySpecialization(String specialization) {
        return adminRepo.getDoctorsBySpecialization(specialization);

    }

    public List<Doctors> getAllDoctors() {
        return adminRepo.getAllDoctors();
    }

    public Doctors getDoctorsById(String d_id) {
        return adminRepo.getDoctorsById(d_id);

    }


    public List<Appointments> getAllAppointments() {
        return adminRepo.fetchAllAppointments();
    }

    public List<Appointments> getAppointmentByPatientId(String p_id) {

        return adminRepo.fetchAppointmentsByPatientId(p_id);
    }

    public List<Appointments> getAppointmentByDoctorId(String d_id) {

        return adminRepo.fetchAppointmentsByDoctorId(d_id);
    }

    public Appointments getAppointmentById(int id) {
        return adminRepo.fetchAppointmentById(id);
    }

    public Optional<Admin> findByEmail(String email) {
        List<Admin> admin = dynamoDBMapper.scan(Admin.class, new DynamoDBScanExpression());
        return admin.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

}
