package org.debadatta.health.service;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.repo.AppointmentsRepo;
import org.debadatta.health.repo.DoctorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentsService {

    @Autowired
    private AppointmentsRepo appointmentsRepo;

    @Autowired
    private DoctorsRepo doctorsRepo;

    public boolean bookAppointment(Appointments appointments) {
        Doctors doctor = doctorsRepo.getDoctorsById(appointments.getD_id());

        if (doctor != null) {
            appointments.setStatus("Booked");
            appointmentsRepo.save(appointments);
            return true;
        } else {
            return false;
        }
    }

    public boolean rescheduleAppointment(int a_id, Appointments updatedAppointments) {
        Appointments existingAppointment = appointmentsRepo.getAppointmentId(a_id);
        if (existingAppointment == null)
            return false;

        Doctors doctor = doctorsRepo.getDoctorsById(updatedAppointments.getD_id());

        if (doctor != null) {
            existingAppointment.setStatus("Rescheduled");

            appointmentsRepo.save(existingAppointment);
            return true;
        } else {
            return false;
        }
    }

    public boolean cancelAppointment(int a_id) {
        Appointments appointments = appointmentsRepo.getAppointmentId(a_id);
        if (appointments != null) {
            appointmentsRepo.delete(appointments);
            return true;
        } else {
            return false;
        }
    }
}
