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
        return patients.getP_id();

    }

    public List<Doctors> getDoctorsBySpecialization(String specialization) {
        Map<String, AttributeValue> doc = new HashMap<>();
        doc.put(":specialization", new AttributeValue().withS(specialization));

        DynamoDBScanExpression se = new DynamoDBScanExpression()
                .withFilterExpression("specialization = :specialization")
                .withExpressionAttributeValues(doc);

        return dynamoDBMapper.scan(Doctors.class, se);
    }

    public Patients updatePatients(String p_id, Patients patient) {
        Patients existingPatient = dynamoDBMapper.load(Patients.class, p_id);

        if (patient.getName() != null)
            existingPatient.setName(patient.getName());
        if (patient.getAge() != null)
            existingPatient.setAge(patient.getAge());
        if (patient.getDisease() != null)
            existingPatient.setDisease(patient.getDisease());
        if (patient.getGender() != null)
            existingPatient.setGender(patient.getGender());
        if (patient.getAddress() != null)
            existingPatient.setAddress(patient.getAddress());
        if (patient.getPhone_no() != null)
            existingPatient.setPhone_no(patient.getPhone_no());

        dynamoDBMapper.save(existingPatient);

        return dynamoDBMapper.load(Patients.class, p_id);

    }

    public String deletePatients(String p_id) {
        Patients load = dynamoDBMapper.load(Patients.class, p_id);
        dynamoDBMapper.delete(load);
        return load.getName() + "get deleted successfully";
    }

}
