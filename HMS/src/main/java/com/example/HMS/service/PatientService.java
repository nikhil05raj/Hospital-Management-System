package com.example.HMS.service;

import com.example.HMS.anotations.Auditable;
import com.example.HMS.dto.PatientDto;
import com.example.HMS.dto.PatientMapper;
import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.Patient;
import com.example.HMS.exception.AppointmentNotFoundException;
import com.example.HMS.exception.PatientNotFoundException;
import com.example.HMS.repository.AppointmentRepository;
import com.example.HMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientMapper patientMapper;

    // Create patient
    @Auditable(action = "CREATE_PATIENT")
    public PatientDto createPatient(PatientDto dto)
    {
        if (dto == null) {
            throw new IllegalArgumentException("Appointment data cannot be null");
        }

        Patient patientEntity = patientMapper.toEntity(dto);
        Patient patient = (Patient) patientRepository.save(patientEntity);

        return patientMapper.toDto(patient);
    }

    // get all patients
    public Page<PatientDto> getAllPatient(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);

        Page<Patient> patients = patientRepository.findAll(pageable);

        return patients.map(patientMapper::toDto);
    }

    // get patient by id
    @Cacheable(value = "patients", key = "#patientId")
    public PatientDto getPatientById(Long patientId)
    {
        if (patientId == null ) {
            throw new IllegalArgumentException("Patient Id cannot be empty");
        }

        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isEmpty()){
            throw new PatientNotFoundException("couldn't find the patient's details ");
        }

        Patient patient1 = patient.get();
        return patientMapper.toDto(patient1);
    }

    // get patient by appointment
    @Cacheable(value = "patientByAppointment", key = "appointmentId")
    public PatientDto fetchPatientByAppointment(Long appointmentId)
    {
        if (appointmentId == null ) {
            throw new IllegalArgumentException("Appointment Id cannot be empty");
        }

        Optional<Appointment> OptApp = appointmentRepository.findById(appointmentId);

        if (OptApp.isEmpty()){
            throw new AppointmentNotFoundException("Failed to find the Appointment with id "+appointmentId);
        }

        Appointment appointment = OptApp.get();

        Patient patient = appointment.getPatient();

        return patientMapper.toDto(patient);
    }

    // Update patient's details
    @Caching(
            put = {
                    @CachePut(value = "patients", key = "#patientId"),
            },
            evict = {
                    @CacheEvict(value = "patientByAppointment", allEntries = true)
            }
    )
    @Auditable(action = "UPDATE_PATIENT")
    public PatientDto updatePatient(Long patientId, PatientDto dto)
    {
        Optional<Patient> existingPatient = patientRepository.findById(patientId);

        if (existingPatient.isEmpty()){
            throw new PatientNotFoundException("failed to find the patient with Id "+ patientId);
        }

        Patient patient = existingPatient.get();

        if (dto.getPatientName() != null) { patient.setPatientName(dto.getPatientName()); }

        if (dto.getPatientAge() != null) { patient.setPatientAge(dto.getPatientAge()); }

        if (dto.getPatientPhone() != null) { patient.setPatientPhone(dto.getPatientPhone()); }

        if (dto.getPatientEmail() != null) { patient.setPatientEmail(dto.getPatientEmail()); }

        if (dto.getGender() != null) { patient.setGender(dto.getGender()); }

        Patient savedPatient = (Patient) patientRepository.save(patient);

        return patientMapper.toDto(savedPatient);
    }

    // delete patient record
    @Caching(
            evict = {
                    @CacheEvict(value = "patients", key = "#patientId"),
                    @CacheEvict(value = "patientByAppointment", allEntries = true),
            }
    )
    @Auditable(action = "DELETE_PATIENT")
    public void deletePatient(Long patientId)
    {
        patientRepository.deleteById(patientId);
    }

    public List<PatientDto> patientAgeGreaterThan(Integer patientAge)
    {
        List<Patient> patient = patientRepository.findByPatientAgeGreaterThan(patientAge);

        return patient.stream().map(patientMapper::toDto).toList();
    }

}
