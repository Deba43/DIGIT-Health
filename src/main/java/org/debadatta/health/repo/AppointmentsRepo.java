package org.debadatta.health.repo;

import java.util.List;

import org.debadatta.health.model.Appointments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

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

}
