package com.example.HMS.dto;

import com.example.HMS.entity.Department;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDto {

    private Long doctorId;

    private String doctorName;
    private String specialization;
    private Boolean doctorAvailable;
    private Long departmentId;

}
