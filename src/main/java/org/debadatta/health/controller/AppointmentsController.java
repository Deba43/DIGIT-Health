package org.debadatta.health.controller;

import org.debadatta.health.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    // getAllAppointments
    // getAppointmentById
    // updateAppointment
    // cancelAppointment
    // getAppointmentByPatientId
    // getAppointmentByDoctorId

}
