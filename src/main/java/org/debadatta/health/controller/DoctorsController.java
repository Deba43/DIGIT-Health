package org.debadatta.health.controller;

import java.util.List;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/doctors")
public class DoctorsController {

    @Autowired
    private DoctorsService doctorsService;

    @PostMapping("/createDoctors")
    public String createDoctors(@RequestBody Doctors doctors) {
        return doctorsService.createDoctors(doctors);
    }


    @GetMapping("/getPatientsByDisease/{disease}")
    public ResponseEntity<List<Patients>> getPatientsByDisease(@PathVariable String disease) {
        List<Patients> patients = doctorsService.getPatientsByDisease(disease);
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/updateDoctors/{d_id}")
    public Doctors updateDoctors(@PathVariable String d_id, @RequestBody Doctors doctors) {
        return doctorsService.updateDoctors(d_id, doctors);

    }

    @DeleteMapping("/deleteDoctors{d_id}")
    public String deleteDoctors(@PathVariable String d_id) {
        return doctorsService.deleteDoctors(d_id);
    }

}
