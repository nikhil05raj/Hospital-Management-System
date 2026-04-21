package com.example.HMS.repository;

import com.example.HMS.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByPatient_PatientId(Long patientId);

    Optional<Bill> findByAppointment_AppointmentId(Long appointmentId);

}
