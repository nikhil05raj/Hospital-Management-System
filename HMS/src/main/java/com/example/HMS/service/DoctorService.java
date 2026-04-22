package com.example.HMS.service;

import com.example.HMS.dto.DoctorDto;
import com.example.HMS.dto.DoctorMapper;
import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.Department;
import com.example.HMS.entity.Doctor;
import com.example.HMS.exception.DoctorNotFoundException;
import com.example.HMS.repository.AppointmentRepository;
import com.example.HMS.repository.DepartmentRepository;
import com.example.HMS.repository.DoctorRepository;
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
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorMapper doctorMapper;

    // Create doctor
    public DoctorDto createDoctor(DoctorDto dto)
    {
        Doctor doctor = doctorMapper.toEntity(dto);

        if (dto.getDepartmentId() != null) {

            Optional<Department> OptDepartment = departmentRepository.findById(dto.getDepartmentId());

            Department department = OptDepartment.get();
            doctor.setDepartment(department);
        }

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toDto(savedDoctor);
    }

    // get doctor by id
    @Cacheable(value = "doctors", key = "#id")
    public DoctorDto getDoctorById(Long doctorId)
    {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isEmpty()) {
            throw new DoctorNotFoundException("Doctor not found with id: " + doctorId);
        }
        Doctor doctor = optionalDoctor.get();
       return doctorMapper.toDto(doctor);
    }

    // get all doctor
    @Cacheable(value = "allDoctors")
    public Page<DoctorDto> getAllDoctor(int page, int size)
    {

            Pageable pageable = PageRequest.of(page,size);
            Page<Doctor> doctors = doctorRepository.findAll(pageable);

            return doctors.map(doctorMapper::toDto);
    }

    // get doctor by department
    @Cacheable(value = "doctorsByDepartment", key = "#departmentName")
    public List<DoctorDto> fetchDoctorsByDepartment(String deptName)
    {
        if (deptName == null || deptName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }

        List<Doctor> doctors = doctorRepository.fetchDoctorsByDepartmentName(deptName);

        return doctors.stream().map(doctorMapper::toDto).toList();
    }

    // get doctors by patient  // Patient → Appointment → Doctor
    @Cacheable(value = "doctorsByPatient", key = "#patientID")
    public List<DoctorDto> fetchDoctorByPatient(Long patientId)
    {
        if (patientId == null || patientId <= 0 ) {
            throw new IllegalArgumentException("Patient Id cannot be empty or negative");
        }

        List<Doctor> doctors = doctorRepository.findDoctorsByPatientId(patientId);

        if (doctors.isEmpty()){
            throw new DoctorNotFoundException("No doctors found for patient with id "+patientId);
        }

        return doctors.stream().map(doctorMapper::toDto).toList();
    }

    // get doctor by appointment id → Appointment → Doctor
    @Cacheable(value = "doctorsByAppointment", key = "#appointmentId")
    public DoctorDto fetchDoctorByAppointment(Long appointmentId)
    {
        if (appointmentId == null ) {
            throw new IllegalArgumentException("appointmentId cannot be empty");
        }

        Optional<Appointment> appointment =  appointmentRepository.findById(appointmentId);

        Appointment appointment1 = appointment.get();

        Doctor doctor = appointment1.getDoctor();

        return doctorMapper.toDto(doctor);
    }

    // get doctors by specialization
    @Cacheable(value = "doctorsBySpec", key = "#specialization")
    public List<DoctorDto> fetchDoctorsBySpecialization(String specialization)
    {
        if (specialization == null ) {
            throw new IllegalArgumentException("Specialization name cannot be empty");
        }

        List<Doctor> doctors = doctorRepository.fetchDoctorsBySpecialization(specialization);

        if (doctors.isEmpty()){
            throw new IllegalArgumentException("Specialization field can't be empty");
        }

        return doctors.stream().map(doctorMapper::toDto).toList();
    }

    // update doctor  doctorId --> Doctor --> update details
    @Caching(
            put = {
                    @CachePut(value = "doctors", key = "#doctorId"),

            },
            evict = {
                    @CacheEvict(value = "allDoctors", allEntries = true),
                    @CacheEvict(value = "doctorsBySpec", allEntries = true),
                    @CacheEvict(value = "doctorsByDepartment", allEntries = true),
                    @CacheEvict(value = "doctorsByPatient", allEntries = true),
                    @CacheEvict(value = "doctorsByAppointment", allEntries = true)
            }
    )
    public DoctorDto updateDoctor(Long doctorId, DoctorDto dto)
    {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isEmpty()) {
            throw new DoctorNotFoundException("Doctor not found with id: " + doctorId);
        }

        Doctor doctor = optionalDoctor.get();

        if (dto.getDepartmentId() != null) {

            Optional<Department> OptDepartment = departmentRepository.findById(dto.getDepartmentId());

            Department department = OptDepartment.get();
            doctor.setDepartment(department);
        }

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toDto(savedDoctor);
    }

    // delete doctor
    @Caching(
            evict = {
                    @CacheEvict(value = "doctors", key = "#doctorId"),
                    @CacheEvict(value = "allDoctors", allEntries = true),
                    @CacheEvict(value = "doctorsByDepartment", allEntries = true),
                    @CacheEvict(value = "doctorsByPatient", allEntries = true),
                    @CacheEvict(value = "doctorsByAppointment", allEntries = true),
                    @CacheEvict(value = "doctorsBySpec", allEntries = true)
            }
    )
    public void deleteDoctor(Long doctorId){

        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isPresent())
        {
            Doctor doctor = optionalDoctor.get();
            doctorRepository.deleteById(doctorId);
        }
        else {
            throw new DoctorNotFoundException("Doctor not found with id: " + doctorId);
        }
    }


}
