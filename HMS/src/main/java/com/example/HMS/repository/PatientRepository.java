package com.example.HMS.repository;

import com.example.HMS.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository<Patient> extends JpaRepository<Patient,Long> {

}
