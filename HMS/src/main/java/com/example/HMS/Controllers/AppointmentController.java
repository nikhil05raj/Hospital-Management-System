package com.example.HMS.Controllers;

import com.example.HMS.Service.AppointmentService;
import com.example.HMS.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;
import com.example.HMS.Service.webhookService;


@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired   // ← VERY IMPORTANT: Inject the webhookService instance!
    private webhookService webhookService;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointmentRequest){
        System.out.println("Creating an Appointment");

        Appointment appointment = appointmentService.createAppointment(appointmentRequest);

        Map<String,Object>payload = new HashMap<>();
        payload.put("appointmentId",appointment.getId());
        payload.put("patientId",appointment.getPatientId());
        payload.put("doctorId",appointment.getDoctorId());
        payload.put("appointmentDate",appointment.getDate());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, payload);

        return appointment;
    }

    @GetMapping
    public Page<Appointment> getAppointments(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size){
        System.out.println("Fetching all the Appointments");
        return appointmentService.getAllAppointment(page,size);
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