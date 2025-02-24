package org.debadatta.health.service;

import org.debadatta.health.enums.AppointmentStatus;
import org.debadatta.health.model.Appointments;

import org.debadatta.health.repo.AppointmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentsService {

    @Autowired
    private AppointmentsRepo appointmentsRepo;

    public boolean bookAppointment(Appointments appointments) {

        if (appointments.getP_id() == null || appointments.getD_id() == null) {
            return false;
        }

        appointments.setStatus(AppointmentStatus.BOOKED);

        appointmentsRepo.save(appointments);

        return true;
    }

    public boolean rescheduleAppointment(String a_id, Appointments updatedAppointments) {
        Appointments existingAppointment = appointmentsRepo.getAppointmentById(a_id);
        if (existingAppointment == null) {
            return false;
        }

        existingAppointment.setA_date(updatedAppointments.getA_date());
        existingAppointment.setA_time(updatedAppointments.getA_time());
        existingAppointment.setStatus(AppointmentStatus.RESCHEDULED);

        appointmentsRepo.save(existingAppointment);
        return true;
    }

    public boolean cancelAppointment(String a_id) {
        Appointments existingAppointment = appointmentsRepo.getAppointmentById(a_id);
        if (existingAppointment != null) {

            existingAppointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentsRepo.save(existingAppointment);

            appointmentsRepo.delete(existingAppointment);
            return true;
        }
        return false;
    }

}
