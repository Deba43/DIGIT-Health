package org.debadatta.health.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsDto {

    @NotNull(message = "Patient ID can't be null")
    private String p_id;

    @NotNull(message = "Doctor ID can't be null")
    private String d_id;

    @NotNull(message = "Appointment date can't be null")
    private Date a_date;

    @NotNull(message = "Appointment time can't be null")
    private String a_time;

    private String status; // Status can be optional (e.g., Scheduled, Completed, Cancelled)
}
