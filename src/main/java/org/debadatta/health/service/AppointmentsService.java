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
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentsService {

    @Autowired
    private AppointmentsRepo appointmentsRepo;
    @Autowired
    private DoctorsRepo doctorsRepo;

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

    public boolean updateAppointment(int a_id, Appointments updatedAppointments) {

        Appointments existingAppointment = appointmentsRepo.getAppointmentId(a_id);
        if (existingAppointment == null)
            return false;

        Doctors doctor = doctorsRepo.getDoctorsById(updatedAppointments.getDoctorId());

        if (doctor != null && doctor.isAvailable(updatedAppointments.getAppointmentDate(),
                updatedAppointments.getAppointmentTime())) {

            existingAppointment.setAppointmentDate(updatedAppointments.getAppointmentDate());
            existingAppointment.setAppiontmentTime(updatedAppointments.getAppointmentTime());
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

    public List<Appointments> getAppointmentByPatientId(String p_id) {

        return appointmentsRepo.fetchAppointmentsByPatientId(p_id);
    }

    public List<Appointments> getAppointmentByDoctorId(String d_id) {

        return appointmentsRepo.fetchAppointmentsByDoctorId(d_id);
    }

    public List<Appointments> getAllAppointments() {
        return appointmentsRepo.fetchAllAppointments();
    }

    public Appointments getAppointmentById(int id) {
        return appointmentsRepo.fetchAppointmentById(id);
    }

}
