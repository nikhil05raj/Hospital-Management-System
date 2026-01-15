package com.example.HMS.repository;

import com.example.HMS.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository<Appointment> extends JpaRepository<Appointment,Long> {
}
