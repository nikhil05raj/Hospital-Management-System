package com.example.HMS.controllers;

import com.example.HMS.dto.PatientDto;
import com.example.HMS.service.PatientService;
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
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final webhookService webhookService;

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto createPatientRequest)
    {
        PatientDto dto = patientService.createPatient(createPatientRequest);
        Map<String,Object> payload = new HashMap<>();

        payload.put("PatientName", dto.getPatientName());
        payload.put("Patient's Age", dto.getPatientAge());
        payload.put("Patient's Gender", dto.getGender());
        payload.put("Patient Phone no.", dto.getPatientPhone());

        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Patient_Created,payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<PatientDto>> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size){
        Page<PatientDto> dto = patientService.getAllPatient(page,size);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId)
    {
        PatientDto dto = patientService.getPatientById(patientId);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @GetMapping("/appointment/{patientId}")
    public ResponseEntity<PatientDto> fetchPatientByAppointment(@PathVariable Long appointmentId)
    {
        PatientDto dto = patientService.fetchPatientByAppointment(appointmentId);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long patientId, @RequestBody PatientDto patientDto)
    {
        PatientDto dto = patientService.updatePatient(patientId, patientDto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable Long patientId)
    {
        patientService.deletePatient(patientId);

        return ResponseEntity.ok("the patient has been deleted with id " + patientId);
    }

    @GetMapping("/ageGreaterThan/{patientAge}")
    public ResponseEntity<List<PatientDto>> patientAgeGreaterThan(@PathVariable Integer patientAge)
    {
        List<PatientDto> dto = patientService.patientAgeGreaterThan(patientAge);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }


}
