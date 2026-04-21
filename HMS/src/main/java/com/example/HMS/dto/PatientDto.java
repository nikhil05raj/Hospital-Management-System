package com.example.HMS.dto;

import com.example.HMS.enums.Gender;
import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDto {

    private Long patientId;
    private String patientName;
    private Integer patientAge;
    private String patientPhone;
    private String patientEmail;
    private Gender gender;

}
