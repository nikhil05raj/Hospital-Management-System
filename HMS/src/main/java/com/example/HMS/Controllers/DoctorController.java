package com.example.HMS.Controllers;

import com.example.HMS.Service.DoctorService;
import com.example.HMS.models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public void createDoctor(@RequestBody Doctor doctor){
        System.out.println("Creating Doctor Profile");
        doctorService.createDoctor(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctor(){
        System.out.println("Retrieve all the Doctors");
        return doctorService.getAllDoctor();
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
