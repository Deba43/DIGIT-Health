package org.debadatta.health.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DoctorsRepo {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public String createDoctors(Doctors doctors) {

        dynamoDBMapper.save(doctors);
        return "Doctor created successfully";
    }

    public Doctors getDoctorsById(String d_id) {
        return dynamoDBMapper.load(Doctors.class, d_id);
    }

    public List<Patients> getPatientsByDisease(String disease) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":disease", new AttributeValue().withS(disease));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("disease = :disease")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(Patients.class, scanExpression);
    }

    public Doctors updateDoctors(String d_id, Doctors doctor) {
        System.out.println("Updating doctor with ID: " + d_id);
        Doctors existingDoctor = dynamoDBMapper.load(Doctors.class, d_id);
        if (existingDoctor == null) {
            System.out.println("Doctor not found: " + d_id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }
        System.out.println("Existing doctor data: " + existingDoctor);

        if (doctor.getName() != null)
            existingDoctor.setName(doctor.getName());
        if (doctor.getEmail() != null)
            existingDoctor.setEmail(doctor.getEmail());
        if (doctor.getPhone_no() != null)
            existingDoctor.setPhone_no(doctor.getPhone_no());
        if (doctor.getAge() != null && !doctor.getAge().isEmpty())
            existingDoctor.setAge(doctor.getAge());
        if (doctor.getSpecialization() != null)
            existingDoctor.setSpecialization(doctor.getSpecialization());
        if (doctor.getExperience() != null)
            existingDoctor.setExperience(doctor.getExperience());
        System.out.println("Updated doctor data: " + existingDoctor);
        dynamoDBMapper.save(existingDoctor);
        return dynamoDBMapper.load(Doctors.class, d_id);
    }

    public String deleteDoctors(String d_id) {
        Doctors load = dynamoDBMapper.load(Doctors.class, d_id);
        dynamoDBMapper.delete(load);
        return load.getName() + " get deleted successfully";
    }

}
