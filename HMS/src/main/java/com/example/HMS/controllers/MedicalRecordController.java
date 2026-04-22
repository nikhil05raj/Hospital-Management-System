package com.example.HMS.controllers;

import com.example.HMS.dto.MedicalRecordDto;
import com.example.HMS.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medicalRecord")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@RequestBody  MedicalRecordDto dto)
    {
        MedicalRecordDto medicalRecordDto = medicalRecordService.createMedicalRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordDto);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecord()
    {
        List<MedicalRecordDto> dto = medicalRecordService.fetchAllRecord();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordById(@PathVariable Long recordId)
    {
        MedicalRecordDto dto = medicalRecordService.fetchRecordById(recordId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordDto>> getRecordByPatient(@PathVariable Long patientId)
    {
        List<MedicalRecordDto> dto = medicalRecordService.fetchRecordByPatient(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<MedicalRecordDto>> getRecordByDoctor(@PathVariable Long doctorId)
    {
        List<MedicalRecordDto> dto = medicalRecordService.fetchRecordByDoctor(doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/visitDate/{visitDate}")
    public ResponseEntity<List<MedicalRecordDto>> getRecordByVisitDate(@PathVariable LocalDate visitDate)
    {
        List<MedicalRecordDto> dto = medicalRecordService.fetchRecordByVisitDate(visitDate);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<MedicalRecordDto>> getRecordByAppointment(@PathVariable Long appointmentId)
    {
        List<MedicalRecordDto> dto = medicalRecordService.fetchRecordByAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/delete/{recordId}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long recordId)
    {
        medicalRecordService.deleteRecord(recordId);
        return ResponseEntity.ok(String.format("the medical record with id %d has been deleted successfully"));
    }

    @PutMapping("/update/{recordId}")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(
            @PathVariable Long recordId,
            @RequestBody MedicalRecordDto dto) {

        MedicalRecordDto updatedDto = medicalRecordService.updateRecord(recordId, dto);

        return ResponseEntity.ok(updatedDto);
    }

}
