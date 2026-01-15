package com.example.HMS.Service;

import com.example.HMS.models.Patient;
import com.example.HMS.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> getAllPatient(int page, int size){
        try{
            System.out.println("Into the service layer : get all patient");
            Pageable pageable = PageRequest.of(page, size);
            return patientRepository.findAll(pageable);
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching all the patients : {}",e.getMessage());
            return null;
        }
    }

    public Patient getPatientById(Long id){
        try{
            System.out.println("Service layer : get patient by id");
            Optional<Patient> patient = patientRepository.findById(id);
            return patient.orElse(null);
        }
        catch (Exception e){
            logger.error("Error occurred while fetching all the patients with id {} : {}",id,e.getMessage());
            return null;
        }
    }

    public Patient createPatient(Patient patient){
        try{
            System.out.println("service layer : Creating Patient");
            patientRepository.save(patient);
            return patient;
        }
        catch (Exception e){
            logger.error("Error occurred while creating patients : {}",e.getMessage());
            return null;
        }
    }

    public void deletePatient(Long id){
        try{
            logger.info("Deleting patient with id {} :",id);
            patientRepository.deleteById(id);
        }
        catch (Exception e){
            logger.error("Error occurred while deleting the patients with id {} : {}",id,e.getMessage());
        }
    }

    public void updatePatient(Long id , Patient updatepatient){
        try{
            Optional<Patient> existingPatient = patientRepository.findById(id);
            if (existingPatient.isPresent()){
                Patient p = existingPatient.get();
                p.setName(updatepatient.getName());
                p.setAge(updatepatient.getAge());
                p.setGender(updatepatient.getGender());

                patientRepository.save(p);
//                return updatepatient;
            }
            else {
                logger.error("Patient with id {} not found ",id);
            }
        }
        catch (Exception e){
            logger.error("Error occurred while updating the patients with id {} : {}",id,e.getMessage());
        }
    }

}
