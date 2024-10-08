package org.debadatta.health.controller;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;

import java.util.Date;
import java.util.List;

import org.debadatta.health.service.AppointmentsService;
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
    public ResponseEntity<String> updateAppointment(@PathVariable int a_id,
            @RequestBody Appointments updatedAppointment) {

        boolean isUpdated = appointmentsService.updateAppointment(a_id, updatedAppointment);

        if (isUpdated) {
            return ResponseEntity.ok("Appointment rescheduled successfully");
        } else {
            return ResponseEntity.badRequest().body("Doctor is unavailable or appointment could not be updated");
        }

    }

    // cancelAppointment

    @DeleteMapping("/cancel/appointments{a_id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable int a_id) {

        boolean isCancel = appointmentsService.cancelAppointment(a_id);

        if (isCancel) {
            return ResponseEntity.ok("Your appointment cancelled successfully");
        } else {
            return ResponseEntity.badRequest().body("Your appointment can't be cancelled");
        }

    }

    // getAppointmentByPatientId
    @GetMapping("/getAppointmentByPatientId{p_id}")
    public ResponseEntity<List<Appointments>> getAppointmentByPatientId(@PathVariable String p_id) {

        List<Appointments> appointments = appointmentsService.getAppointmentByPatientId(p_id);

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    // getAppointmentByDoctorId

    @GetMapping("/getAppointmentByDoctorId{d_id}")
    public ResponseEntity<List<Appointments>> getAppointmentByDoctorId(@PathVariable String d_id) {

        List<Appointments> appointments = appointmentsService.getAppointmentByDoctorId(d_id);

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    // getAllAppointments

    @GetMapping("/getAllAppointment")
    public ResponseEntity<List<Appointments>> getAllAppointments() {
        List<Appointments> appointments = appointmentsService.getAllAppointments();

        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build(); // Return 204 if no appointments found
        }
    }
    // getAppointmentById

    @GetMapping("/{id}")
    public ResponseEntity<Appointments> getAppointmentById(@PathVariable int id) {// the id is for the URL path of the
                                                                                  // incoming HTTP request
        Appointments appointment = appointmentsService.getAppointmentById(id);

        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if appointment not found
        }
    }

}
