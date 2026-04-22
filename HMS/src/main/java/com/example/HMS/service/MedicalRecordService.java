package com.example.HMS.service;

import com.example.HMS.dto.MedicalRecordDto;
import com.example.HMS.dto.MedicalRecordMapper;
import com.example.HMS.entity.*;
import com.example.HMS.exception.AppointmentNotFoundException;
import com.example.HMS.exception.MedicalRecordNotFoundException;
import com.example.HMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalRecordRepository medicalRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    // Create medical record
    public MedicalRecordDto createMedicalRecord(MedicalRecordDto dto)
    {
        if (dto.getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment ID is required");
        }

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException(
                        "Appointment not found with id: " + dto.getAppointmentId()));

        Patient patient = appointment.getPatient();
        Doctor doctor = appointment.getDoctor();

        if (patient == null || doctor == null) {
            throw new IllegalStateException("Appointment is missing Patient or Doctor");
        }

        Prescription prescription = null;
        if (dto.getPrescriptionId() != null) {
            prescription = prescriptionRepository.findById(dto.getPrescriptionId())
                    .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + dto.getPrescriptionId()));
        }

        MedicalRecord record = medicalRecordMapper.toEntity(dto);

        record.setAppointment(appointment);
        record.setPatient(patient);
        record.setDoctor(doctor);

        if (prescription != null) { record.setPrescription(prescription); }

        MedicalRecord savedRecord = medicalRepository.save(record);

        return medicalRecordMapper.toDto(savedRecord);
    }

    // get all record
    @Cacheable(value = "allRecord")
    public List<MedicalRecordDto> fetchAllRecord()
    {
        List<MedicalRecord> medicalRecords = medicalRepository.findAll();

        return medicalRecords.stream().map(medicalRecordMapper::toDto).toList();
    }

    // get record by id
    @Cacheable(value = "records", key = "#id")
    public MedicalRecordDto fetchRecordById(Long recordId)
    {
        Optional<MedicalRecord> medicalRecord = medicalRepository.findById(recordId);

        if (medicalRecord.isEmpty()){
            throw new MedicalRecordNotFoundException(String.format("Failed to fetch the medical record for id %d", recordId));
        }

        MedicalRecord record = medicalRecord.get();

        return medicalRecordMapper.toDto(record);
    }

    // get record by patient
    @Cacheable(value = "recordByPatient", key = "#patientId")
    public List<MedicalRecordDto> fetchRecordByPatient(Long patientId)
    {
        List<MedicalRecord> records = medicalRepository.findByPatientPatientIdOrderByVisitDateDesc(patientId);

        if (records.isEmpty()){
            throw new MedicalRecordNotFoundException(String.format("Failed to fetch the medical record for Patient Id %d", patientId));
        }

        return records.stream().map(medicalRecordMapper::toDto).toList();
    }

    // get record by Doctor
    @Cacheable(value = "recordByDoctor", key = "#doctorId")
    public List<MedicalRecordDto> fetchRecordByDoctor(Long doctorId)
    {
        List<MedicalRecord> records = medicalRepository.findByDoctorDoctorIdOrderByVisitDateDesc(doctorId);

        if (records.isEmpty()){
            throw new MedicalRecordNotFoundException(String.format("Failed to fetch the medical record for Doctor Id %d", doctorId));
        }

        return records.stream().map(medicalRecordMapper::toDto).toList();

    }

     // get record by appointment
    @Cacheable(value = "recordByAppointment", key = "#appointmentId")
    public List<MedicalRecordDto> fetchRecordByAppointment(Long appointmentId)
    {
        List<MedicalRecord> medicalRecord = medicalRepository.findByAppointment_AppointmentId(appointmentId);

        if (medicalRecord.isEmpty()){
            throw new MedicalRecordNotFoundException(String.format("Failed to fetch the medical record for id %d", appointmentId));
        }

        return medicalRecord.stream().map(medicalRecordMapper::toDto).toList();
    }

    // get record by date
    public List<MedicalRecordDto> fetchRecordByVisitDate(LocalDate visitDate)
    {
        List<MedicalRecord> records = medicalRepository.findByVisitDate(visitDate);

        if (records.isEmpty()){
            throw new MedicalRecordNotFoundException("Failed to fetch the medical record for date : "+ visitDate);
        }

        return records.stream().map(medicalRecordMapper::toDto).toList();
    }

    // delete the record by id
    @Caching(
            evict = {
                    @CacheEvict(value = "allRecord", allEntries = true),
                    @CacheEvict(value = "records", allEntries = true),
                    @CacheEvict(value = "recordByPatient", allEntries = true),
                    @CacheEvict(value = "recordByDoctor", allEntries = true),
                    @CacheEvict(value = "recordByAppointment", allEntries = true)
            }
    )
    public void deleteRecord(Long recordId)
    {
        medicalRepository.deleteById(recordId);
    }

    @Caching(
            put = {
                    @CachePut(value = "records", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "allRecord", allEntries = true),
                    @CacheEvict(value = "recordByPatient", allEntries = true),
                    @CacheEvict(value = "recordByDoctor", allEntries = true),
                    @CacheEvict(value = "recordByAppointment", allEntries = true)
            }
    )
    public MedicalRecordDto updateRecord(Long recordId, MedicalRecordDto dto)
    {
        if (recordId == null || recordId <= 0) {
            throw new IllegalArgumentException("Medical Record ID must be valid and positive");
        }
        
        MedicalRecord existingRecord = medicalRepository.findById(recordId)
                .orElseThrow(() -> new MedicalRecordNotFoundException(
                        "Medical Record not found with id: " + recordId));

        if (dto.getDiagnosis() != null) {
            existingRecord.setDiagnosis(dto.getDiagnosis());
        }
        if (dto.getTreatment() != null) {
            existingRecord.setTreatment(dto.getTreatment());
        }
        if (dto.getVisitDate() != null) {
            existingRecord.setVisitDate(dto.getVisitDate());
        }

        MedicalRecord savedRecord = medicalRepository.save(existingRecord);

        return medicalRecordMapper.toDto(savedRecord);
    }


}
