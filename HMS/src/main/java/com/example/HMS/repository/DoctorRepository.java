package com.example.HMS.repository;

import com.example.HMS.entity.Department;
import com.example.HMS.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.specialization) = LOWER(:specialization)")
    List<Doctor> fetchDoctorsBySpecialization(@Param("specialization") String specialization);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.department.departmentName) = LOWER(:deptName)")
    List<Doctor> fetchDoctorsByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT DISTINCT d FROM Doctor d " +
            "JOIN d.appointments a " +
            "WHERE a.patient.patientId = :patientId")
    List<Doctor> findDoctorsByPatientId(@Param("patientId") Long patientId);
}
