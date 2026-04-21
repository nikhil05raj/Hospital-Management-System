package com.example.HMS.dto;

import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.MedicalRecord;
import com.example.HMS.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicalRecordMapper {

    private final AppointmentRepository appointmentRepository;

    // Dto ---> Entity
    public MedicalRecord toEntity(MedicalRecordDto dto)
    {
        if (dto == null) return null;

        MedicalRecord record = new MedicalRecord();
        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatment(dto.getTreatment());
        record.setVisitDate(dto.getVisitDate());

        return record;
    }

    // Entity ---> Dto
    public MedicalRecordDto toDto(MedicalRecord record)
    {
        if (record == null) return null;

        MedicalRecordDto dto = new MedicalRecordDto();

        dto.setRecordId(record.getRecordId());
        dto.setDiagnosis(record.getDiagnosis());
        dto.setTreatment(record.getTreatment());
        dto.setVisitDate(record.getVisitDate());

        // Safe ID extraction
        if (record.getAppointment() != null) {
            dto.setAppointmentId(record.getAppointment().getAppointmentId());

            if (record.getAppointment().getPatient() != null) {
                dto.setPatientId(record.getAppointment().getPatient().getPatientId());
            }
            if (record.getAppointment().getDoctor() != null) {
                dto.setDoctorId(record.getAppointment().getDoctor().getDoctorId());
            }
        }

        if (record.getPrescription() != null) {
            dto.setPrescriptionId(record.getPrescription().getPrescriptionId());
        }


        return dto;
    }
}
