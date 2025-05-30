package org.debadatta.health.model;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component

@Scope("prototype")
@DynamoDBTable(tableName = "Admin")
public class Admin extends User {

    @DynamoDBHashKey(attributeName = "admin_id")
    private String admin_id = UUID.randomUUID().toString();

    @DynamoDBAttribute(attributeName = "name")
    @NotNull(message = "Name can't be null")
    private String name;

    @DynamoDBAttribute(attributeName = "password")
    @NotNull(message = "Password can't be null")
    @Size(min = 8, message = "Your Password must be at least 6 character long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "Password must contain at least one letter, one number, and one special character")
    private String password;

    @DynamoDBAttribute(attributeName = "role")
    @NotNull(message = "Role can't be null")
    private String role; // SUPER_ADMIN,MANAGER

    @DynamoDBAttribute(attributeName = "email")
    @NotNull(message = "Email can't be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    private String email;

    @DynamoDBAttribute(attributeName = "phone_no")
    @NotNull(message = "Mobile Number can't be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    private String phone_no;

    @DynamoDBAttribute(attributeName = "age")
    @NotNull(message = "Age can't be null")
    private String age;

}
