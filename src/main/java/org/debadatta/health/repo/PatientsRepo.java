package org.debadatta.health.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PatientsRepo {

    @Autowired
    final private DynamoDBMapper dynamoDBMapper;

    public String createPatients(Patients patients) {
        dynamoDBMapper.save(patients);
        return patients.getId();

    }

    public List<Patients> getAllPatients() {
        return dynamoDBMapper.scan(Patients.class, new DynamoDBScanExpression());
    }

    public Patients getPatientsById(String p_id) {
        return dynamoDBMapper.load(Patients.class, p_id);
    }

    public List<Doctors> getDoctorsBySpecialization(String specialization) {
        Map<String, AttributeValue> doc = new HashMap<>();
        doc.put(":specialization", new AttributeValue().withS(specialization));

        DynamoDBScanExpression se = new DynamoDBScanExpression()
                .withFilterExpression("specialization = :specialization")
                .withExpressionAttributeValues(doc);

        return dynamoDBMapper.scan(Doctors.class, se);
    }

    public Patients updatePatients(String p_id, Patients patients) {
        Patients load = dynamoDBMapper.load(Patients.class, p_id);
        load.setName(patients.getName());
        load.setAge(patients.getAge());
        load.setDisease(patients.getDisease());
        load.setGender(patients.getGender());
        load.setAddress(patients.getAddress());
        load.setPhoneNo(patients.getPhoneNo());
        dynamoDBMapper.save(load);

        return dynamoDBMapper.load(Patients.class, p_id);

    }

    public String deletePatients(String p_id) {
        Patients load = dynamoDBMapper.load(Patients.class, p_id);
        dynamoDBMapper.delete(load);
        return load.getName() + "get deleted successfully";
    }

}
