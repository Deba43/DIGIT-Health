package org.debadatta.health.controller;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import java.util.Date;
import org.debadatta.health.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/appoint")
public class AppointmentsController {

    @Autowired
    private AppointmentsService appointmentsService;

    // bookAppointment

    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointments appointments) {
        boolean isBooked = appointmentsService.bookAppointment(appointments);

        if (isBooked) {
            return ResponseEntity.ok("Your Appointment Booked Successfully");

        } else {
            return ResponseEntity.badRequest().body("Doctor is unavailable at the requested time!");
        }

    }

    // updateAppointment
    @PutMapping("/reschedule{a_id}")
    public ResponseEntity<String> upadteAppointment(@PathVariable String a_id,
            @RequestBody Appointments updatedAppointment) {

        boolean isUpdated = appointmentsService.updateAppointments(a_id, updatedAppointment);

        if (isUpdated) {
            return ResponseEntity.ok("Appointment rescheduled successfully");
        } else {
            return ResponseEntity.badRequest().body("Doctor is unavailable or appointment could not be updated");
        }

    }

    // getAllAppointments
    // getAppointmentById

    // cancelAppointment
    // getAppointmentByPatientId
    // getAppointmentByDoctorId

}
