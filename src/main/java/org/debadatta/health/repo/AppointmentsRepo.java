package org.debadatta.health.repo;

import java.util.List;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class AppointmentsRepo {

    @Autowired
    final private DynamoDBMapper dynamoDBMapper;

    public void save(Appointments appointments) {
        dynamoDBMapper.save(appointments);
    }

    public Appointments getAppointmentId(int a_id) {
        return dynamoDBMapper.load(Appointments.class, a_id);
    }

    public void delete(Appointments appointments) {
        dynamoDBMapper.delete(appointments);
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

    public List<Appointments> fetchAllAppointments() {
        return dynamoDBMapper.scan(Appointments.class, new DynamoDBScanExpression());
    }

    public Appointments fetchAppointmentById(int id) {
        return dynamoDBMapper.load(Appointments.class, id);
    }

}
