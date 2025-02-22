package org.debadatta.health.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsDto {

    @NotNull(message = "Name can't be null")
    private String name;

    @NotNull(message = "Password can't be null")
    @Size(min = 6, message = "Your Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "Password must contain at least one letter, one number, and one special character")
    private String password;

    @NotNull(message = "Email can't be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    private String email;

    @NotNull(message = "Mobile Number can't be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    private String phone_no;

    @NotNull(message = "Age can't be null")
    private String age;

    @NotNull(message = "Specialization status cannot be null")
    private String specialization;

    @NotNull(message = "Experience can't be null")
    private String experience;

}
