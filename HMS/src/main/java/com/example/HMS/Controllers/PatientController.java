package com.example.HMS.Controllers;

import com.example.HMS.Service.BillService;
import com.example.HMS.Service.PatientService;
import com.example.HMS.Service.webhookService;
import com.example.HMS.enums.EventType;
import com.example.HMS.models.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private webhookService webhookService;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping
    public Page<Patient> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "2") int size){
        System.out.println("fetching the Patients");
        return patientService.getAllPatient(page,size);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patientRequest){
        System.out.println("creating the patient ");

        Patient patient = patientService.createPatient(patientRequest);
        Map<String,Object> payload = new HashMap<>();

        payload.put("PatientId",patient.getId());
        payload.put("PatientName",patient.getName());
        payload.put("PatientGender",patient.getGender());
        payload.put("PatientAge",patient.getAge());

        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Patient_Created,payload);

        return patient;
    }

    @GetMapping("/{id}")
    public Patient getPatientBy(@PathVariable Long id){
        System.out.println("Fetching by id");
        return patientService.getPatientById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id){
        System.out.println("deleting the Patient");
        patientService.deletePatient(id);
    }

    @PutMapping("/{id}")
    public void updatePatient(@PathVariable Long id, @RequestBody Patient patient){
        System.out.println("Updating the patient details");
        patientService.updatePatient(id,patient);
    }
}
