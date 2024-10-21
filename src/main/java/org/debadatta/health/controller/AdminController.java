package org.debadatta.health.controller;

import java.util.List;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getAllpatients")
    public ResponseEntity<List<Patients>> getAllpatients() {
        List<Patients> patients = adminService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getPatientsById/{p_id}")
    public ResponseEntity<Patients> getPatientsById(@PathVariable String p_id) {
        Patients patients = adminService.getPatientsById(p_id);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getDoctorsById/{d_id}")
    public ResponseEntity<Doctors> getDoctorsById(@PathVariable String d_id) {
        Doctors doctors = adminService.getDoctorsById(d_id);
        if (doctors != null) {
            return ResponseEntity.ok(doctors);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<List<Doctors>> getAllDoctors() {
        List<Doctors> doctors = adminService.getAllDoctors();
        return ResponseEntity.ok(doctors);

    }

}
