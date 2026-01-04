package com.example.HMS.Service;

import com.example.HMS.models.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    public List<Patient> getAllPatient(){
        try{
            System.out.println("Into the service layer : get all patient");
            return null;
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
            return null;
        }
    }

    public Patient getPatientById(Long id){
        try{
            System.out.println("Service layer : get patient by id");
            return null;
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
            return null;
        }
    }

    public Patient createPatient(Patient patient){
        try{
            System.out.println("service layer : Creating Patient");
            return null;
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
            return null;
        }
    }

    public void deletePatient(Long id){
        try{
            System.out.println("service layer : Deleting     Patient");
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
        }
    }

    public void updatePatient(Long id){
        try{
            System.out.println("service layer : Updating Patient");
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
        }
    }

}
