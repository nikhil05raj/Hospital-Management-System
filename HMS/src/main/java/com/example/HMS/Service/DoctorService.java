package com.example.HMS.Service;

import com.example.HMS.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    public List<Doctor> getAllDoctor(){
        try{
            System.out.println("service layer : Doctor List");
            return null;
        }
        catch (Exception e){
            System.out.println("Error Message : " + e.getMessage());
            return null;
        }
    }

    public Doctor getDoctorById(Long id){
        try{
            System.out.println("service layer : get Doctor by Id");
            return null;
        }
        catch (Exception e){
            System.out.println("Error Message : " + e.getMessage());
            return null;
        }
    }

    public void createDoctor(Doctor doctor){
        try {
            System.out.println("service layer : Creating Doctors");
        }
        catch (Exception e) {
            System.out.println("Error Message : "+ e.getMessage());
        }
    }

    public void deleteDoctor(Long id){
        try{
            System.out.println("service layer : Deleting  Doctor");
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
        }
    }

    public void updateDoctor(Long id){
        try{
            System.out.println("service layer : Updating Doctor");
        }
        catch (Exception e){
            System.out.println("Error message" + e.getMessage());
        }
    }

}
