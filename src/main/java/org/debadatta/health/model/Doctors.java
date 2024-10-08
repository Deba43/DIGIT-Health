package org.debadatta.health.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component

@Scope("prototype")
@DynamoDBTable(tableName = "Doctors")
public class Doctors {

    
    
    @DynamoDBAutoGeneratedKey
    @DynamoDBHashKey(attributeName = "d_id")
    private String d_id;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "password")
    private String password;
    @DynamoDBAttribute(attributeName = "age")
    private int age;
    @DynamoDBAttribute(attributeName = "specialization")
    private String specialization;
    @DynamoDBAttribute(attributeName = "experience")
    private int experience;
    @DynamoDBAttribute(attributeName = "availability")
    private Map<Date, List<String>> availability;

    public String getId() {
        return d_id;
    }

    public void setId(String d_id) {
        this.d_id = d_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String speacialization) {
        this.specialization = speacialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Map<Date, List<String>> getAvailabilty() {
        return availability;
    }

    public void setAvailablity(Map<Date, List<String>> availabilty) {
        this.availability = availabilty;
    }

    public boolean isAvailable(Date a_date, String a_time) {

        if (availability.containsKey(a_date)) {
            List<String> availableTimes = availability.get(a_time);
            return availableTimes.contains(a_time);
        }
        return false;

    }

}
