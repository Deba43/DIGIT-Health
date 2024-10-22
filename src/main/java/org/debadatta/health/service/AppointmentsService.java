package org.debadatta.health.service;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;

import java.util.Date;
import java.util.List;

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

        if (doctor != null
                && doctor.isAvailable(appointments.getA_date(), appointments.getA_time())) {

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

        if (doctor != null && doctor.isAvailable(updatedAppointments.getA_date(),
                updatedAppointments.getA_time())) {

            existingAppointment.setA_date(updatedAppointments.getA_date());
            existingAppointment.setA_time(updatedAppointments.getA_time());
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
            // appointments.setStatus("Cancelled");
            appointmentsRepo.delete(appointments);

            return true;
        } else {
            return false;
        }

    }
}
