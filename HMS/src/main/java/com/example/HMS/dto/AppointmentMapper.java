package com.example.HMS.dto;

import com.example.HMS.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    // Dto ---> Entity
    public Appointment toEntity(AppointmentDto dto)
    {

        if (dto == null) { return null; }

        Appointment appointment = new Appointment();

        appointment.setTime(dto.getTime());
        appointment.setDate(dto.getDate());
        appointment.setStatus(dto.getStatus());
        appointment.setPatientEmail(dto.getEmail());

        return appointment;
    }

    // Entity ---> Dto
    public AppointmentDto toDto(Appointment appointment)
    {
        if (appointment == null){ return null; }

        AppointmentDto dto =new AppointmentDto();

        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setTime(appointment.getTime());
        dto.setDate(appointment.getDate());
        dto.setStatus(appointment.getStatus());
        dto.setEmail(appointment.getPatientEmail());

        // Safe null check + ID extraction
        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getDoctorId());
        }

        if (appointment.getPatient() != null) {
            dto.setPatientId(appointment.getPatient().getPatientId());
        }

        return dto;
    }
}
