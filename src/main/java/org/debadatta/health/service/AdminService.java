package org.debadatta.health.service;

import java.util.List;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;

    public List<Patients> getAllPatients() {
        return adminRepo.getAllPatients();
    }

    public Patients getPatientsById(String p_id) {
        return adminRepo.getPatientsById(p_id);
    }

    public Doctors getDoctorsById(String d_id) {
        return adminRepo.getDoctorsById(d_id);

    }

    public List<Doctors> getAllDoctors() {
        return adminRepo.getAllDoctors();
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

}
