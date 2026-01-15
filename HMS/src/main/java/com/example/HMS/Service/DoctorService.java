package com.example.HMS.Service;

import com.example.HMS.models.Doctor;
import com.example.HMS.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private DoctorRepository doctorRepository;

    public Page<Doctor> getAllDoctor(int page, int size){
        try{
            System.out.println("service layer : Doctor List");
            Pageable pageable = PageRequest.of(page,size);
            return doctorRepository.findAll(pageable);
        }
        catch (Exception e){
            logger.error("Error occurred while fetching all the Doctors : {}",e.getMessage());
            return null;
        }
    }

    public Doctor getDoctorById(Long id){
        try{
            System.out.println("service layer : get Doctor by Id");
            return null;
        }
        catch (Exception e){
            logger.error("Error occurred while fetching the Doctors with id {} : {}",id,e.getMessage());
            return null;
        }
    }

    public Doctor createDoctor(Doctor doctor){
        try {
            System.out.println("service layer : Creating Doctors");
            return doctor;
        }
        catch (Exception e) {
            logger.error("Error occurred while creating the Doctor : {}",e.getMessage());
            return null;
        }
    }

    public void deleteDoctor(Long id){
        try{
            System.out.println("service layer : Deleting  Doctor");
        }
        catch (Exception e){
            logger.error("Error occurred while deleting the doctor : {}",e.getMessage());
        }
    }

    public void updateDoctor(Long id){
        try{
            System.out.println("service layer : Updating Doctor");
        }
        catch (Exception e){
            logger.error("Error occurred while updating the doctor : {}",e.getMessage());
        }
    }
}
