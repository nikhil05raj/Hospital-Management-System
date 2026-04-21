package com.example.HMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalRecordDto {

    private Long recordId;

    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private Long prescriptionId;

    private String diagnosis;
    private String treatment;
    private LocalDate visitDate;

}
