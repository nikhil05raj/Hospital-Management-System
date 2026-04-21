package com.example.HMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionDto {

    private Long prescriptionId;
    private String medicine;
    private String dosage;
    private String instructions;

    private Long medicalRecordId;
}
