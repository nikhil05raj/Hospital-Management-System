package com.example.HMS.Controllers;

import com.example.HMS.Service.AppointmentService;
import com.example.HMS.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment){
        System.out.println("Creating an Appointment");
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping
    public List<Appointment> getAppointments(){
        System.out.println("Fetching all the Appointments");
        return appointmentService.getAllAppointment();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id){
        System.out.println("Fetching appointment by id "+id );
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}")
    public void updateAppointmentById(@PathVariable Long id){
        System.out.println("Updating appointment by id "+id );
        appointmentService.updateAppointmentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointmentById(@PathVariable Long id){
        System.out.println("Deleting appointment by id "+id );
        appointmentService.deleteAppointmentById(id);
    }

}
