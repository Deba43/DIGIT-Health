package org.debadatta.health.service;

import java.util.List;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.repo.PatientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientsService {

    @Autowired
    private PatientsRepo patientsRepo;

    public String createPatients(Patients patients) {
        return patientsRepo.createPatients(patients);
    }

    public List<Patients> getAllPatients() {
        return patientsRepo.getAllPatients();
    }

    public Patients getPatientsById(String p_id) {
        return patientsRepo.getPatientsById(p_id);
    }

    public List<Doctors> getDoctorsBySpecialization(String specialization) {
        return patientsRepo.getDoctorsBySpecialization(specialization);
    }

    public Patients updatePatients(String p_id, Patients patients) {
        return patientsRepo.updatePatients(p_id, patients);

    }

    public String deletePatients(String p_id) {
        return patientsRepo.deletePatients(p_id);

    }
}
