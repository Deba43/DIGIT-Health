package org.debadatta.health.service;

import java.util.List;
import java.util.Optional;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.repo.DoctorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorsService {

    @Autowired
    private DoctorsRepo doctorsRepo;
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public String createDoctors(Doctors doctors) {
        return doctorsRepo.createDoctors(doctors);
    }

    public List<Patients> getPatientsByDisease(String disease) {
        return doctorsRepo.getPatientsByDisease(disease);
    }

    public Doctors updateDoctors(String d_id, Doctors doctors) {
        return doctorsRepo.updateDoctors(d_id, doctors);
    }

    public String deleteDoctors(String d_id) {
        return doctorsRepo.deleteDoctors(d_id);
    }

    public Optional<Doctors> findByEmail(String email) {
        List<Doctors> doc = dynamoDBMapper.scan(Doctors.class, new DynamoDBScanExpression());
        return doc.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

}
