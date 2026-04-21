package com.example.HMS.dto;

import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {

    // Dto ---> Entity
    public Bill toEntity(BillDto dto)
    {
        if (dto == null) return null;

        Bill bill = new Bill();
        bill.setStatus(dto.getStatus());
        bill.setBillDate(dto.getBillDate());
        bill.setAmount(dto.getAmount());
        bill.setPaymentMethod(dto.getPaymentMethod());

        return bill;
    }

    // Entity ---> Dto
    public BillDto toDto(Bill bill)
    {
        if (bill == null) return null;

        BillDto dto = new BillDto();
        dto.setBillId(bill.getBillId());
        dto.setStatus(bill.getStatus());
        dto.setBillDate(bill.getBillDate());
        dto.setAmount(bill.getAmount());
        dto.setPaymentMethod(bill.getPaymentMethod());

        if (bill.getPatient() != null){
            dto.setPatientId(bill.getPatient().getPatientId());
        }

        if (bill.getAppointment() != null){
            dto.setAppointmentId(bill.getAppointment().getAppointmentId());
        }

        return dto;
    }

}
