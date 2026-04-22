package com.example.HMS.controllers;

import com.example.HMS.dto.DoctorDto;
import com.example.HMS.service.DoctorService;
import com.example.HMS.service.webhookService;
import com.example.HMS.enums.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final webhookService webhookService;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorRequest)
    {
        DoctorDto doctor = doctorService.createDoctor(doctorRequest);

        Map<String,Object>payload = new HashMap<>();
        payload.put("doctorId",doctor.getDoctorId());
        payload.put("doctorName",doctor.getDoctorId());
        payload.put("Speciality",doctor.getSpecialization());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Doctor_Created, payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long doctorId)
    {
        DoctorDto doctorDto = doctorService.getDoctorById(doctorId);
        return ResponseEntity.status(HttpStatus.FOUND).body(doctorDto);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorDto>> getAllDoctor(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size)
    {
        Page<DoctorDto> doctorDto = doctorService.getAllDoctor(page,size);
        return ResponseEntity.ok(doctorDto);
    }

    @GetMapping("/department/{deptName}")
    public ResponseEntity<List<DoctorDto>> fetchDoctorsByDepartment(@PathVariable String deptName)
    {
        List<DoctorDto> doctors = doctorService.fetchDoctorsByDepartment(deptName);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<DoctorDto>> fetchDoctorByPatient(@PathVariable Long patientId)
    {
        List<DoctorDto> doctors = doctorService.fetchDoctorByPatient(patientId);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<DoctorDto> fetchDoctorByAppointment(@PathVariable Long appointmentId)
    {
        DoctorDto doctors = doctorService.fetchDoctorByAppointment(appointmentId);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorDto>> fetchDoctorsBySpecialization(@PathVariable String specialization)
    {
        List<DoctorDto> doctors = doctorService.fetchDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/update/{DoctorId}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long DoctorId,
                                                  @RequestBody DoctorDto doctorDto)
    {
        DoctorDto dto = doctorService.updateDoctor(DoctorId, doctorDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable Long doctorId)
    {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Doctor deleted successfully with id "+doctorId);
    }
}
