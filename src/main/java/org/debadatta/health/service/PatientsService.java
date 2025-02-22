package org.debadatta.health.service;

import java.util.List;
import java.util.Optional;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.model.User;
import org.debadatta.health.repo.PatientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientsService {

    @Autowired
    private PatientsRepo patientsRepo;
    @Autowired
    DynamoDBMapper dynamoDBMapper;

    public String createPatients(Patients patients) {
        return patientsRepo.createPatients(patients);
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

    public Optional<Patients> findByEmail(String email) {
        List<Patients> pat = dynamoDBMapper.scan(Patients.class, new DynamoDBScanExpression());
        return pat.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
}
