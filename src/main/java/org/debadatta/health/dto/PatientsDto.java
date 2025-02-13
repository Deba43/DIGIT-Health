package org.debadatta.health.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientsDto {

    @NotNull(message = "Doctor ID can't be null")
    private String d_id;

    @NotNull(message = "Name can't be null")
    private String name;

    @NotNull(message = "Password can't be null")
    @Size(min = 6, message = "Your Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "Password must contain at least one letter, one number, and one special character")
    private String password;

    @NotNull(message = "Age can't be null")
    private Integer age;

    @NotNull(message = "Disease can't be null")
    private String disease;

    @NotNull(message = "Gender can't be null")
    private String gender;

    private String address;

    @NotNull(message = "Mobile Number can't be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    private String phone_no;

    @NotNull(message = "Email can't be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    private String email;
}
