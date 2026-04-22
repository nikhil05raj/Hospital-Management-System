package com.example.HMS.controllers;

import com.example.HMS.dto.AppointmentDto;
import com.example.HMS.enums.AppointmentStatus;
import com.example.HMS.service.AppointmentService;
import com.example.HMS.entity.Patient;
import com.example.HMS.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.HMS.service.webhookService;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    Patient patient;

    private final AppointmentService appointmentService;
    private final webhookService webhookService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentRequest)
    {
        AppointmentDto savedAppointment = appointmentService.createAppointment(appointmentRequest);

        Map<String,Object>payload = new HashMap<>();
        payload.put("doctorName", savedAppointment.getDoctorId());
        payload.put("patientName", savedAppointment.getPatientId());
        payload.put("patientEmail", savedAppointment.getEmail());
        payload.put("appointmentDate", savedAppointment.getDate());

        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, AppointmentStatus.APPOINTMENT_SCHEDULED, payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentDto>> getAllAppointment(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size)
    {
        Page<AppointmentDto> appointmentDto =  appointmentService.getAllAppointment(page,size);

        return ResponseEntity.ok(appointmentDto);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId)
    {
        AppointmentDto appointmentDto = appointmentService.getAppointmentById(appointmentId);

        return ResponseEntity.ok(appointmentDto);
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentDto> updateAppointmentById(@PathVariable Long appointmentId,
                                                                @RequestBody AppointmentDto dto)
    {
        AppointmentDto appointmentDto = appointmentService.updateAppointmentById(appointmentId, dto);

        Map<String,Object>payload = new HashMap<>();
        payload.put("Appointment Id",appointmentId);
        payload.put("Doctor Id", appointmentDto.getDoctorId());
        payload.put("Patient Id",appointmentDto.getPatientId());
        payload.put("Appointment date",appointmentDto.getDate());
        payload.put("Patient email",appointmentDto.getEmail());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, AppointmentStatus.APPOINTMENT_UPDATED, payload);

        return ResponseEntity.status(HttpStatus.OK).body(appointmentDto);
    }

    @PutMapping("/reschedule/{appointmentId}")
    public ResponseEntity<AppointmentDto> rescheduleAppointment(@PathVariable Long appointmentId,
                                      @RequestBody AppointmentDto dto)
    {
        AppointmentDto appointmentDto =  appointmentService.rescheduleAppointment(appointmentId, dto);

        Map<String,Object>payload = new HashMap<>();
        payload.put("appointmentDate",dto.getDate());
        payload.put("appointmentTime",dto.getTime());

        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, AppointmentStatus.APPOINTMENT_RESCHEDULED, payload);

        return ResponseEntity.ok(appointmentDto);
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId)
    {
        Appointment deletedAppointment = appointmentService.cancelAppointment(appointmentId);

        Map<String,Object>payload = new HashMap<>();
        payload.put("Staus",String.format("Appointment has been cancelled with id %d",appointmentId));
        payload.put("patientEmail", deletedAppointment.getPatientEmail());

        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, AppointmentStatus.APPOINTMENT_CANCELLED, payload);
        return ResponseEntity.ok(String.format("the appointment with id : %d has been cancelled",appointmentId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDate(@PathVariable LocalDate date)
    {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByDate(date);

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentByDoctor(@PathVariable Long doctorId)
    {
        List<AppointmentDto> appointments = appointmentService.getAppointmentByDoctor(doctorId);

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentByPatient(@PathVariable Long patientId)
    {
        List<AppointmentDto> appointments = appointmentService.getAppointmentByPatient(patientId);

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDto>> fetchAppointmentByStatus(@PathVariable AppointmentStatus status)
    {
        List<AppointmentDto> appointments = appointmentService.getAppointmentByStatus(status);

        return ResponseEntity.ok(appointments);
    }


}