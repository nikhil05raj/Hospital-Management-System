package com.example.HMS.repository;

import com.example.HMS.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository<Doctor> extends JpaRepository<Doctor,Long> {
}
