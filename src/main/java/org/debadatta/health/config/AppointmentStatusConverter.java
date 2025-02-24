package org.debadatta.health.config;

import org.debadatta.health.enums.AppointmentStatus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class AppointmentStatusConverter implements DynamoDBTypeConverter<String, AppointmentStatus> {
    @Override
    public String convert(AppointmentStatus status) {
        return status.toString();
    }

    @Override
    public AppointmentStatus unconvert(String value) {
        return AppointmentStatus.valueOf(value);
    }
}
