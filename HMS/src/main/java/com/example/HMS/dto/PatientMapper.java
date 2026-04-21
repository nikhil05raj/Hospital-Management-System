package com.example.HMS.dto;

import com.example.HMS.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    // DTO → Entity
    public Patient toEntity(PatientDto dto) {

        if (dto == null) { return null; }

        Patient patient = new Patient();
        patient.setPatientName(dto.getPatientName());
        patient.setPatientAge(dto.getPatientAge());
        patient.setGender(dto.getGender());
        patient.setPatientPhone(dto.getPatientPhone());
        patient.setPatientEmail(dto.getPatientEmail());

        return patient;
    }

    // Entity → DTO
    public PatientDto toDto(Patient patient) {

        if (patient == null) { return null; }

        PatientDto dto = new PatientDto();
        dto.setPatientId(patient.getPatientId());
        dto.setPatientName(patient.getPatientName());
        dto.setPatientAge(patient.getPatientAge());
        dto.setGender(patient.getGender());
        dto.setPatientPhone(patient.getPatientPhone());
        dto.setPatientEmail(patient.getPatientEmail());

        return dto;
    }
}
