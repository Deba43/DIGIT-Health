package org.debadatta.health.service;

import java.util.List;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.repo.DoctorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class DoctorsService {

    @Autowired
    private DoctorsRepo doctorsRepo;

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

}
