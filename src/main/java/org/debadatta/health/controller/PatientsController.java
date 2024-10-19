package org.debadatta.health.controller;

import java.util.List;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.service.PatientsService;
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
@RequestMapping("/api/patients")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;

    @PostMapping("/createPatients")
    public String createPatients(@RequestBody Patients patients) {
        return patientsService.createPatients(patients);
    }

    @GetMapping("/getAllpatients")
    public ResponseEntity<List<Patients>> getAllpatients() {
        List<Patients> patients = patientsService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getPatientsById/{p_id}")
    public ResponseEntity<Patients> getPatientsById(@PathVariable String p_id) {
        Patients patients = patientsService.getPatientsById(p_id);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getDoctorsBySpecialization/{specialization}")
    public ResponseEntity<List<Doctors>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctors> ds = patientsService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(ds);
    }

    @PutMapping("/updatePatients/{p_id}")
    public Patients updatePatients(@PathVariable String p_id, @RequestBody Patients patients) {
        return patientsService.updatePatients(p_id, patients);
    }

    @DeleteMapping("/deletePatients/{p_id}")
    public String deletePatients(@PathVariable String p_id) {
        return patientsService.deletePatients(p_id);
    }

}
