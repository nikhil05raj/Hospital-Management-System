package com.example.HMS.Controllers;

import com.example.HMS.Service.AppointmentService;
import com.example.HMS.enums.EventType;
import com.example.HMS.models.Appointment;
import com.example.HMS.models.Doctor;
import com.example.HMS.models.Patient;
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

    private Patient patient;
    private Doctor doctor;

    @Autowired   // ← VERY IMPORTANT: Inject the webhookService instance!
    private webhookService webhookService;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointmentRequest){
        System.out.println("Creating an Appointment");

        Appointment appointment = appointmentService.createAppointment(appointmentRequest);

        Map<String,Object>payload = new HashMap<>();
        payload.put("patientEmail", patient.getEmail());
        payload.put("doctorName", doctor.getName());
        payload.put("appointmentDate",appointment.getDate());

        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Appointment_Created, payload);

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

        Appointment appointment = appointmentService.updateAppointmentById(id);

        Map<String,Object>payload = new HashMap<>();
        payload.put("patientEmail", patient.getEmail());
        payload.put("doctorName", doctor.getName());
        payload.put("appointmentDate",appointment.getDate());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Appointment_Updated, payload);
    }

    @PutMapping("/{id}")
    public void rescheduleAppointment(@PathVariable Long id){

        Appointment appointment = appointmentService.rescheduleAppointment(id);

        Map<String,Object>payload = new HashMap<>();
        payload.put("patientEmail", patient.getEmail());
        payload.put("appointmentDate",appointment.getDate());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Appointment_Rescheduled, payload);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointmentById(@PathVariable Long id){

        Appointment appointment = appointmentService.deleteAppointmentById(id);

        Map<String,Object>payload = new HashMap<>();
        payload.put("patientEmail", patient.getEmail());
        payload.put("doctorName", doctor.getName());
        payload.put("appointmentDate",appointment.getDate());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, EventType.Appointment_deleted, payload);
    }
}