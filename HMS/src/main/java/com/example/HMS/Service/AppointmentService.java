package com.example.HMS.Service;

import com.example.HMS.models.Appointment;
import com.example.HMS.repository.AppointmentRepository;
import com.example.HMS.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment){
        try {
        System.out.println("Service layer : Create Appointment");
            return appointment;
        }
        catch (Exception e) {
            logger.error("Error occurred while creating the Appointment : {}",e.getMessage());
            return null;
        }
    }

    public Page<Appointment> getAllAppointment(int page, int size){
        try {
            System.out.println("Service layer : Get all Appointment");
            Pageable pageable = PageRequest.of(page, size);
            return appointmentRepository.findAll(pageable);
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching all the appointments : {}",e.getMessage());
            return null;
        }
    }

    public Appointment getAppointmentById(Long id){
        try {
            System.out.println("Service layer : Get Appointment");
            return null;
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching appointment by id {} : {}",id,e.getMessage());
            return null;
        }
    }

    public Appointment updateAppointmentById(Long id){
        try {
            System.out.println("Service layer : Update Appointment");
            return null;
        }
        catch (Exception e) {
            logger.error("Error occurred while updating the appointment by id {} : {}",id,e.getMessage());
            return null;
        }
    }

    public Appointment rescheduleAppointment(Long id){
        try {
            System.out.println("Service layer : Reschedule Appointment");
            return null;
        }
        catch (Exception e) {
            logger.error("Error occurred while Rescheduling the appointment by id {} : {}",id,e.getMessage());
            return null;
        }
    }

    public Appointment deleteAppointmentById(Long id){
        try {
            System.out.println("Service layer : Delete Appointment");
            return null;
        }
        catch (Exception e) {
            logger.error("Error occurred while deleting appointment by id {} : {}",id,e.getMessage());
            return null;
        }
    }
}
