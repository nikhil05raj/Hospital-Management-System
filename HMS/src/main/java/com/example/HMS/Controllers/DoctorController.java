package com.example.HMS.Controllers;

import com.example.HMS.Service.DoctorService;
import com.example.HMS.Service.webhookService;
import com.example.HMS.enums.EventType;
import com.example.HMS.models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired   // ← VERY IMPORTANT: Inject the webhookService instance!
    private webhookService webhookService;

    @GetMapping
    public Page<Doctor> getAllDoctor(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "2") int size){
        System.out.println("Retrieve all the Doctors");
        return doctorService.getAllDoctor(page,size);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctorRequest){
        System.out.println("Creating Doctor Profile");

        Doctor doctor = doctorService.createDoctor(doctorRequest);

        Map<String,Object>payload = new HashMap<>();
        payload.put("doctorId",doctor.getId());
        payload.put("doctorName",doctor.getName());
        payload.put("Speciality",doctor.getSpeciality());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Doctor_Created, payload);
        return doctor;
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id){
        System.out.println("Retrieve all the Doctors");
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public void updateDoctorById(@PathVariable Long id){
        System.out.println("updating Doctor's details");
        doctorService.updateDoctor(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctorById(@PathVariable Long id){
        System.out.println("Deleting specific Detail of Doctor");
        doctorService.deleteDoctor(id);
    }
}
