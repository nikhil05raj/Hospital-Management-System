package com.example.HMS.dto;

import com.example.HMS.enums.AppointmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDto {

    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private LocalTime time;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private String email;

}
