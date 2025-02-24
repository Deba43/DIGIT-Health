package org.debadatta.health.controller;

import java.util.List;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.service.AppointmentsService;
import org.debadatta.health.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;
    @Autowired
    private AppointmentsService appointmentsService;

    @PostMapping("/createPatients")
    public String createPatients(@RequestBody Patients patients) {
        return patientsService.createPatients(patients);
    }

    @PutMapping("/updatePatients/{p_id}")
    public Patients updatePatients(@PathVariable String p_id, @RequestBody Patients patients) {
        return patientsService.updatePatients(p_id, patients);
    }

    @DeleteMapping("/deletePatients/{p_id}")
    public String deletePatients(@PathVariable String p_id) {
        return patientsService.deletePatients(p_id);
    }

    @GetMapping("/getDoctorsBySpecialization/{specialization}")
    public ResponseEntity<List<Doctors>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctors> ds = patientsService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(ds);
    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointments appointments) {
        boolean isBooked = appointmentsService.bookAppointment(appointments);

        if (isBooked) {
            return ResponseEntity.ok("Your appointment has been booked successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid appointment request!");
        }
    }

    @PutMapping("/rescheduleAppointment/{a_id}")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable String a_id,
            @RequestBody Appointments updatedAppointment) {

        boolean isUpdated = appointmentsService.rescheduleAppointment(a_id, updatedAppointment);

        if (isUpdated) {
            return ResponseEntity.ok("Appointment rescheduled successfully");
        } else {
            return ResponseEntity.badRequest().body("Appointment could not be updated!");
        }
    }

    @DeleteMapping("/cancelAppointment/{a_id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable String a_id) {

        boolean isCancelled = appointmentsService.cancelAppointment(a_id);

        if (isCancelled) {
            return ResponseEntity.ok("Your appointment has been cancelled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Appointment not found or cannot be cancelled.");
        }
    }

}
