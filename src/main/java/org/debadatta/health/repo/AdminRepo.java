package org.debadatta.health.repo;

import java.util.List;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
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

}
