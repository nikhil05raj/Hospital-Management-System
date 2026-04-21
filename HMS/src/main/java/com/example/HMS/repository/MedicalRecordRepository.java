package com.example.HMS.repository;

import com.example.HMS.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientPatientIdOrderByVisitDateDesc(Long patientId);

    List<MedicalRecord> findByDoctorDoctorIdOrderByVisitDateDesc(Long patientId);

    List<MedicalRecord> findByVisitDate(LocalDate visitDate);

    List<MedicalRecord> findByAppointment_AppointmentId(Long appointmentId);

}
