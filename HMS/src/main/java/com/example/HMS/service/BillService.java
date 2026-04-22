package com.example.HMS.service;

import com.example.HMS.dto.BillDto;
import com.example.HMS.dto.BillMapper;
import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.Bill;
import com.example.HMS.entity.Patient;
import com.example.HMS.exception.AppointmentNotFoundException;
import com.example.HMS.exception.BillNotFoundException;
import com.example.HMS.exception.PatientNotFoundException;
import com.example.HMS.repository.AppointmentRepository;
import com.example.HMS.repository.BillRepository;
import com.example.HMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillMapper billMapper;

    // Create Bill
    public BillDto createBill(BillDto dto)
    {
        Bill bill = billMapper.toEntity(dto);

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

            bill.setPatient(patient);
        }

        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

            bill.setAppointment(appointment);
        }

        Bill savedBill = billRepository.save(bill);
        return billMapper.toDto(savedBill);
    }

    // get bill by id
    @Cacheable(value = "bills", key = "#id")
    public BillDto getBillById(Long billId)
    {
        if (billId <= 0){
            throw new IllegalArgumentException("the bill id can't be null");
        }

        Bill bill = billRepository.findById(billId)
                .orElseThrow(()-> new BillNotFoundException(
                        "Failed to find the bill with id " + billId));

        return billMapper.toDto(bill);
    }

    // get all bills
    @Cacheable(value = "allBills")
    public Page<BillDto> getAllBill(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);

        Page<Bill> bills = billRepository.findAll(pageable);

        return bills.map(billMapper::toDto);

    }

    // Update bill by id
    @Caching(
            put = {
                    @CachePut(value = "bills", key = "#id"),
            },
            evict = {
                    @CacheEvict(value = "allBills", allEntries = true),
                    @CacheEvict(value = "billsByAppointment", allEntries = true),
                    @CacheEvict(value = "billsByPatient", allEntries = true)
            }
    )
    public BillDto updateBillById(Long billId , BillDto dto)
    {
        if (billId <= 0){
            throw new IllegalArgumentException("the bill id can't be null");
        }

        Bill bill = billRepository.findById(billId)
                .orElseThrow(()-> new BillNotFoundException(
                        "Failed to find the bill with id " + billId));


        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(()-> new PatientNotFoundException(
                        "Failed to find patient with the id : "+dto.getPatientId()));

        bill.setPatient(patient);

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(()-> new AppointmentNotFoundException(
                        "Failed to find the Appointment with id : "+dto.getAppointmentId()));

        bill.setAppointment(appointment);

        bill.setBillDate(dto.getBillDate());
        bill.setStatus(dto.getStatus());
        bill.setPaymentMethod(dto.getPaymentMethod());
        bill.setAmount(dto.getAmount());

        billRepository.save(bill);

        return billMapper.toDto(bill);
    }

    // Delete bill by id
    @Caching(evict = {
            @CacheEvict(value = "bills", key = "#id"),
            @CacheEvict(value = "allBills", allEntries = true),
            @CacheEvict(value = "billsByPatient", allEntries = true),
            @CacheEvict(value = "billsByAppointment", allEntries = true)
    })
    public void deleteBillById(Long billId)
    {
        billRepository.deleteById(billId);
    }

    // get bill by Patient
    @Cacheable(value = "billsByPatient", key = "#patientId")
    public List<BillDto> getBillByPatient(Long patientId)
    {
        if (patientId <= 0 ){
            throw new IllegalArgumentException("Patient ID must be valid and positive");
        }

        List<Bill> bills = billRepository.findByPatient_PatientId(patientId);

        return bills.stream().map(billMapper::toDto).toList();
    }

    // get bill by Appointment
    @Cacheable(value = "billsByAppointment", key = "#appointmentId")
    public BillDto getBillByAppointment(Long appointmentId)
    {
        if (appointmentId <= 0){
            throw new IllegalArgumentException("the bill id can't be null");
        }

        Bill bill = billRepository.findByAppointment_AppointmentId(appointmentId)
                .orElseThrow(()-> new BillNotFoundException(
                        "Failed to find the bill with id " + appointmentId));

        return billMapper.toDto(bill);
    }

}