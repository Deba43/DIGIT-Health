package org.debadatta.health.service;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.repo.AppointmentsRepo;
import org.debadatta.health.repo.DoctorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentsService {

    @Autowired
    private AppointmentsRepo appointmentsRepo;
    @Autowired
    private DoctorsRepo doctorsRepo;

    @PostMapping("/book")
    public boolean bookAppointment(Appointments appointments) {
        Doctors doctor = doctorsRepo.getDoctorsById(appointments.getDoctorId());

        if (doctor != null
                && doctor.isAvailable(appointments.getAppointmentDate(), appointments.getAppointmentTime())) {

            appointments.setStatus("Booked");
            appointmentsRepo.save(appointments);
            return true;
        } else {
            return false;
        }

    }

}
