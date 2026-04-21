package com.example.HMS.dto;

import com.example.HMS.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    // Dto ---> Entity
    public Prescription toEntity(PrescriptionDto dto)
    {
        if (dto == null) { return null; }

        Prescription prescription = new Prescription();
        prescription.setDosage(dto.getDosage());
        prescription.setMedicine(dto.getMedicine());
        prescription.setInstructions(dto.getInstructions());

        return prescription;
    }

    // Entity ---> Dto
    public PrescriptionDto toDto(Prescription prescription)
    {
        if (prescription == null){ return null; }

        PrescriptionDto dto =new PrescriptionDto();

        dto.setPrescriptionId(prescription.getPrescriptionId());
        dto.setDosage(prescription.getDosage());
        dto.setMedicine(prescription.getMedicine());
        dto.setInstructions(prescription.getInstructions());

        if (prescription.getMedicalRecord() != null) {
            dto.setMedicalRecordId(prescription.getMedicalRecord().getRecordId());
        }

        return dto;
    }

}
