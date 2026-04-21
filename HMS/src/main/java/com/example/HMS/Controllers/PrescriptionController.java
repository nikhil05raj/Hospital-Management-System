package com.example.HMS.Controllers;

import com.example.HMS.dto.PrescriptionDto;
import com.example.HMS.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(@RequestBody PrescriptionDto dto)
    {
        PrescriptionDto prescriptionDto = prescriptionService.createPrescription(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionDto);
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> fetchAllPrescriptions()
    {
        List<PrescriptionDto> dtos = prescriptionService.fetchAllPrescription();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionDto> fetchPrescriptionById(@PathVariable Long prescriptionId)
    {
        PrescriptionDto dto = prescriptionService.fetchPrescriptionById(prescriptionId);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/medicalRecord/{recordId}")
    public ResponseEntity<PrescriptionDto> fetchPrescriptionByMedicalRecord(@PathVariable Long recordId)
    {
        PrescriptionDto dto = prescriptionService.fetchPrescriptionByMedicalRecord(recordId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> fetchPrescriptionByPatient(@PathVariable Long patientId)
    {
        List<PrescriptionDto> dtos = prescriptionService.fetchPrescriptionByPatient(patientId);

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/update/{prescriptionId}")
    public ResponseEntity<PrescriptionDto> updatePrescription(@PathVariable Long prescriptionId, @RequestBody PrescriptionDto dto)
    {
        PrescriptionDto dto1 = prescriptionService.updatePrescription(prescriptionId, dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto1);
    }

    @DeleteMapping("/delete/{prescriptionId}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long prescriptionId)
    {
        prescriptionService.deletePrescription(prescriptionId);

        return ResponseEntity.ok(String.format("Prescription with Id %d has been deleted successfully.",prescriptionId));
    }


}
