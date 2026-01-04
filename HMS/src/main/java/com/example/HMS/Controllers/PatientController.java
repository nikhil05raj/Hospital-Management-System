package com.example.HMS.Controllers;

import com.example.HMS.Service.PatientService;
import com.example.HMS.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients(){
        System.out.println("fetching the Patients");
        return patientService.getAllPatient();
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient){
        System.out.println("creating the patient ");
        return patientService.createPatient(patient);
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
    public void updatePatient(@PathVariable Long id){
        System.out.println("Updating the patient details");
        patientService.updatePatient(id);
    }
}
