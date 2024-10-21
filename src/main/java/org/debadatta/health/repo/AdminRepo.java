package org.debadatta.health.repo;

import java.util.List;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class AdminRepo {

    @Autowired
    final private DynamoDBMapper dynamoDBMapper;

    public List<Patients> getAllPatients() {
        return dynamoDBMapper.scan(Patients.class, new DynamoDBScanExpression());
    }

    public Patients getPatientsById(String p_id) {
        return dynamoDBMapper.load(Patients.class, p_id);
    }

    public Doctors getDoctorsById(String d_id) {
        return dynamoDBMapper.load(Doctors.class, d_id);
    }

    public List<Doctors> getAllDoctors() {
        return dynamoDBMapper.scan(Doctors.class, new DynamoDBScanExpression());
    }

    public List<Appointments> fetchAllAppointments() {
        return dynamoDBMapper.scan(Appointments.class, new DynamoDBScanExpression());
    }

    public List<Appointments> fetchAppointmentsByPatientId(String p_id) {

        Appointments appointmentKey = new Appointments();
        appointmentKey.setP_id(p_id);

        DynamoDBQueryExpression<Appointments> queryExpression = new DynamoDBQueryExpression<Appointments>()
                .withHashKeyValues(appointmentKey);// Patient Id should be a partition key or use GSI

        return dynamoDBMapper.query(Appointments.class, queryExpression);

    }

    public List<Appointments> fetchAppointmentsByDoctorId(String d_id) {

        Appointments appointmentKey = new Appointments();
        appointmentKey.setD_id(d_id);

        DynamoDBQueryExpression<Appointments> queryExpression = new DynamoDBQueryExpression<Appointments>()
                .withHashKeyValues(appointmentKey);// doctorId should be a partition key or use GSI

        return dynamoDBMapper.query(Appointments.class, queryExpression);

    }

    public Appointments fetchAppointmentById(int id) {
        return dynamoDBMapper.load(Appointments.class, id);
    }

}
