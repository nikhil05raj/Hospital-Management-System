package com.example.HMS.repository;

import com.example.HMS.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p " +
            "WHERE p.medicalRecord.patient.patientId = :patientId")
    List<Prescription> findPrescriptionsByPatientId(@Param("patientId") Long patientId);

    Optional<Prescription> findByMedicalRecord_RecordId(Long medicalRecordId);

}
