package com.example.HMS.repository;

import com.example.HMS.entity.Appointment;
import com.example.HMS.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByDate(LocalDate appointmentDate);

    List<Appointment> findByDoctorDoctorIdOrderByDateDesc(Long doctorId);

    List<Appointment> findByPatientPatientId(Long patientId);

}
