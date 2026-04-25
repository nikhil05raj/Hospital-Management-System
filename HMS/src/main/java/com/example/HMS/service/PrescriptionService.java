package com.example.HMS.service;

import com.example.HMS.anotations.Auditable;
import com.example.HMS.dto.PrescriptionDto;
import com.example.HMS.dto.PrescriptionMapper;
import com.example.HMS.entity.MedicalRecord;
import com.example.HMS.entity.Prescription;
import com.example.HMS.exception.MedicalRecordNotFoundException;
import com.example.HMS.exception.PrescriptionNotFoundException;
import com.example.HMS.repository.MedicalRecordRepository;
import com.example.HMS.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final MedicalRecordRepository medicalRecordRepository;

    MedicalRecord medicalRecord;

    // create prescription
    @Auditable(action = "CREATE_PRESCRIPTION")
    public PrescriptionDto createPrescription(PrescriptionDto dto)
    {
        Prescription prescription = prescriptionMapper.toEntity(dto);

        MedicalRecord medicalRecord = null;
        if (dto.getMedicalRecordId() != null){
            medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId())
                    .orElseThrow(()-> new MedicalRecordNotFoundException(
                            "Failed to find the medical record with id "+dto.getMedicalRecordId()));


            prescription.setMedicalRecord(medicalRecord); // setting the medical record here
            medicalRecord.setPrescription(prescription);
        }

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        return prescriptionMapper.toDto(savedPrescription);
    }

    // get all prescription
    public List<PrescriptionDto> fetchAllPrescription()
    {
        List<Prescription> prescriptions = prescriptionRepository.findAll();

        return prescriptions.stream().map(prescriptionMapper::toDto).toList();
    }

    // get prescription by id
    @Cacheable(value = "prescriptions", key = "#prescriptionId")
    public PrescriptionDto fetchPrescriptionById(Long prescriptionId)
    {
        if (prescriptionId == null || prescriptionId <= 0) {
            throw new IllegalArgumentException("Prescription ID must be valid and positive");
        }

        // Correct repository method
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionNotFoundException(
                        "Failed to find the prescription with id: " + prescriptionId));

        return prescriptionMapper.toDto(prescription);
    }

    // get prescription by MedicalRecord
    @Cacheable(value = "prescriptionByMedicalRecord", key = "#recordId")
    public PrescriptionDto fetchPrescriptionByMedicalRecord(Long recordId)
    {
        if (recordId == null || recordId <= 0) {
            throw new IllegalArgumentException("Medical Record ID must be valid and positive");
        }

        Prescription prescription = prescriptionRepository
                .findByMedicalRecord_RecordId(recordId)
                .orElseThrow(() -> new PrescriptionNotFoundException(
                        "Failed to find the prescription for medical record id: " + recordId));

        return prescriptionMapper.toDto(prescription);
    }

    // get prescription by patient
    @Cacheable(value = "prescriptionByPatient", key = "#patientId")
    public List<PrescriptionDto> fetchPrescriptionByPatient(Long patientId)
    {
        if (patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient ID must be valid");
        }

        List<Prescription> prescriptions = prescriptionRepository.findPrescriptionsByPatientId(patientId);

        if (prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("No prescription found for patient id: " + patientId);
        }

        return prescriptions.stream()
                .map(prescriptionMapper::toDto)
                .toList();
    }

    // update prescription
    @Caching(
            put = {
                    @CachePut(value = "prescriptions", key = "#prescriptionId")
            },
            evict = {
                    @CacheEvict(value = "prescriptionByPatient", allEntries = true),
                    @CacheEvict(value = "prescriptionByMedicalRecord", allEntries = true)
            }
    )
    @Auditable(action = "UPDATE_PRESCRIPTION")
    public PrescriptionDto updatePrescription(Long prescriptionId, PrescriptionDto dto)
    {
        Prescription prescription = null;

        if (dto.getPrescriptionId() == null ) {
            prescription = prescriptionRepository.findById(prescriptionId)
                    .orElseThrow(()->new PrescriptionNotFoundException(
                            "Prescription not found with id +"+ dto.getPrescriptionId()));
        }

        if (dto.getInstructions() != null){
            prescription.setInstructions(dto.getInstructions());
        }

        if (dto.getDosage() != null){
            prescription.setDosage(dto.getDosage());
        }

        if (dto.getMedicine() != null){
            prescription.setMedicine(dto.getMedicine());
        }

        MedicalRecord record = null;
        if (dto.getMedicalRecordId() != null){
            record = medicalRecordRepository.findById(dto.getMedicalRecordId())
                    .orElseThrow(()-> new MedicalRecordNotFoundException(
                            "Failed to find the medical record with id "+dto.getMedicalRecordId()));
        }

        prescription.setMedicalRecord(record);

        prescriptionRepository.save(prescription);

        return prescriptionMapper.toDto(prescription);

    }

    // delete prescription
    @Caching(
            evict = {
                    @CacheEvict(value = "prescriptions", key = "#prescriptionId"),
                    @CacheEvict(value = "prescriptionByMedicalRecord", allEntries = true),
                    @CacheEvict(value = "prescriptionByPatient", allEntries = true)
            }
    )
    @Auditable(action = "DELETE_PRESCRIPTION")
    public void deletePrescription(Long prescriptionId)
    {
        if (prescriptionId == null ) {
            throw new IllegalArgumentException("Prescription ID must be valid");
        }

        prescriptionRepository.deleteById(prescriptionId);
    }

}
