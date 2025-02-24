package org.debadatta.health.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<Patients>> getAllpatients() {
        List<Patients> patients = adminService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getPatientsByDisease/{disease}")
    public ResponseEntity<?> getPatientsByDisease(@PathVariable String disease) {
        try {
            List<Patients> patients = adminService.getPatientsByDisease(disease);
            if (patients.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching Patients: " + e.getMessage());
        }
    }

    @GetMapping("/getPatientsById/{p_id}")
    public ResponseEntity<Patients> getPatientsById(@PathVariable String p_id) {
        Patients patients = adminService.getPatientsById(p_id);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<List<Doctors>> getAllDoctors() {
        List<Doctors> doctors = adminService.getAllDoctors();
        return ResponseEntity.ok(doctors);

    }

    @GetMapping("/getDoctorsBySpecialization/{specialization}")
    public ResponseEntity<?> getDoctorsBySpecialization(@PathVariable String specialization) {
        try {
            List<Doctors> doc = adminService.getDoctorsBySpecialization(specialization);
            if (doc.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(doc);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching Doctors: " + e.getMessage());
        }
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

    @GetMapping("/getAllAppointment")
    public ResponseEntity<List<Appointments>> getAllAppointments() {
        List<Appointments> appointments = adminService.getAllAppointments();

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build(); 
        }
    }



    @GetMapping("/getAppointmentById/{id}")
    public ResponseEntity<Appointments> getAppointmentById(@PathVariable String id) {
                                                                                  
        Appointments appointment = adminService.getAppointmentById(id);

        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }


}
