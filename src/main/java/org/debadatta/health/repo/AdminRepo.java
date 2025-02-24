package org.debadatta.health.repo;

import java.util.List;
import java.util.Map;

import org.debadatta.health.model.Appointments;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class AdminRepo {

    @Autowired
    private final DynamoDBMapper dynamoDBMapper;

    public List<Patients> getAllPatients() {
        return dynamoDBMapper.scan(Patients.class, new DynamoDBScanExpression());
    }

    public List<Patients> getPatientsByDisease(String disease) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("disease = :disease")
                .withExpressionAttributeValues(Map.of(":disease", new AttributeValue().withS(disease)));

        return dynamoDBMapper.scan(Patients.class, scanExpression);
    }

    public Patients getPatientsById(String p_id) {
        return dynamoDBMapper.load(Patients.class, p_id);
    }

    public List<Doctors> getDoctorsBySpecialization(String specialization) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("specialization = :specialization")
                .withExpressionAttributeValues(Map.of(":specialization", new AttributeValue().withS(specialization)));

        return dynamoDBMapper.scan(Doctors.class, scanExpression);
    }

    public List<Doctors> getAllDoctors() {
        return dynamoDBMapper.scan(Doctors.class, new DynamoDBScanExpression());
    }

    public Doctors getDoctorsById(String d_id) {
        return dynamoDBMapper.load(Doctors.class, d_id);
    }

    public List<Appointments> fetchAllAppointments() {
        return dynamoDBMapper.scan(Appointments.class, new DynamoDBScanExpression());
    }

    public Appointments fetchAppointmentById(String id) {
        return dynamoDBMapper.load(Appointments.class, id);
    }

}
