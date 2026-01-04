package com.example.HMS.Service;

import com.example.HMS.models.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {


    public Appointment createAppointment(Appointment appointment){
        try {
        System.out.println("Service layer : Create Appointment");
            return null;
        }
        catch (Exception e) {
            System.out.println("Error Message :" + e.getMessage());
            return null;
        }
    }

    public List<Appointment> getAllAppointment(){
        try {
            System.out.println("Service layer : Create Appointment");
            return null;
        }
        catch (Exception e) {
            System.out.println("Error Message :" + e.getMessage());
            return null;
        }
    }

    public Appointment getAppointmentById(Long id){
        try {
            System.out.println("Service layer : Create Appointment");
            return null;
        }
        catch (Exception e) {
            System.out.println("Error Message :" + e.getMessage());
            return null;
        }
    }

    public Appointment updateAppointmentById(Long id){
        try {
            System.out.println("Service layer : Create Appointment");
            return null;
        }
        catch (Exception e) {
            System.out.println("Error Message :" + e.getMessage());
            return null;
        }
    }

    public void deleteAppointmentById(Long id){
        try {
            System.out.println("Service layer : Create Appointment");
        }
        catch (Exception e) {
            System.out.println("Error Message :" + e.getMessage());
        }
    }


}
