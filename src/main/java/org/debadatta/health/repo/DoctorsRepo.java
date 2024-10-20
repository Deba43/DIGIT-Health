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
public class DoctorsRepo {

    @Autowired
    final private DynamoDBMapper dynamoDBMapper;

    public String createDoctors(Doctors doctors) {

        dynamoDBMapper.save(doctors);
        return doctors.getD_id();
    }

    public List<Doctors> getAllDoctors() {
        return dynamoDBMapper.scan(Doctors.class, new DynamoDBScanExpression());
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

        Doctors existingDoctor = dynamoDBMapper.load(Doctors.class, d_id);
        if (doctor.getName() != null)
            existingDoctor.setName(doctor.getName());
        if (doctor.getAge() != 0)
            existingDoctor.setAge(doctor.getAge());
        if (doctor.getSpecialization() != null)
            existingDoctor.setSpecialization(doctor.getSpecialization());
        if (doctor.getExperience() != 0)
            existingDoctor.setExperience(doctor.getExperience());
        if (doctor.getAvailability() != null)
            existingDoctor.setAvailability(doctor.getAvailability());

        dynamoDBMapper.save(existingDoctor);

        return dynamoDBMapper.load(Doctors.class, d_id);

    }

    public String deleteDoctors(String d_id) {
        Doctors load = dynamoDBMapper.load(Doctors.class, d_id);
        dynamoDBMapper.delete(load);
        return load.getName() + " get deleted successfully";
    }

}
