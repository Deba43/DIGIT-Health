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

    DynamoDBMapper dynamoDBMapper;

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

    @GetMapping("/getAllAppointment")
    public ResponseEntity<List<Appointments>> getAllAppointments() {
        List<Appointments> appointments = adminService.getAllAppointments();

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build(); // Return 204 if no appointments found
        }
    }

    @GetMapping("/getAppointmentByPatientId/{p_id}")
    public ResponseEntity<List<Appointments>> getAppointmentByPatientId(@PathVariable String p_id) {

        List<Appointments> appointments = adminService.getAppointmentByPatientId(p_id);

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/getAppointmentByDoctorId/{d_id}")
    public ResponseEntity<List<Appointments>> getAppointmentByDoctorId(@PathVariable String d_id) {

        List<Appointments> appointments = adminService.getAppointmentByDoctorId(d_id);

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/getAppointmentById/{id}")
    public ResponseEntity<Appointments> getAppointmentById(@PathVariable int id) {// the id is for the URL path of the
                                                                                  // incoming HTTP request
        Appointments appointment = adminService.getAppointmentById(id);

        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if appointment not found
        }
    }

    public List<Patients> getPatientsByDoctorId(String doctorId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":doctorId", new AttributeValue().withS(doctorId));

        DynamoDBQueryExpression<Patients> queryExpression = new DynamoDBQueryExpression<Patients>()
                .withIndexName("d_id-index") // Using the GSI
                .withConsistentRead(false) // Must be false for GSIs
                .withKeyConditionExpression("d_id = :doctorId")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.query(Patients.class, queryExpression);
    }

}
