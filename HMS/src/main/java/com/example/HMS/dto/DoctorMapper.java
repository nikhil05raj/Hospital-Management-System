package com.example.HMS.dto;

import com.example.HMS.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    // DTO → Entity
    public Doctor toEntity(DoctorDto dto) {

        if (dto == null) { return null; }

        Doctor doctor = new Doctor();

        if (dto.getDoctorName() != null) {
            doctor.setDoctorName(dto.getDoctorName());
        }
        if (dto.getSpecialization() != null) {
            doctor.setSpecialization(dto.getSpecialization());
        }
        if (dto.getDoctorAvailable() != null) {
            doctor.setDoctorAvailable(dto.getDoctorAvailable());
        }

        return doctor;
    }

    // Entity → DTO
    public DoctorDto toDto(Doctor doctor) {

        if (doctor == null) { return null; }

        DoctorDto dto = new DoctorDto();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setDoctorName(doctor.getDoctorName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setDoctorAvailable(doctor.getDoctorAvailable());
        dto.setDepartmentId(doctor.getDepartment().getDepartmentId());

        return dto;
    }
}
