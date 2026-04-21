package com.example.HMS.dto;

import com.example.HMS.entity.Appointment;
import com.example.HMS.entity.Patient;
import com.example.HMS.enums.PaymentMethod;
import com.example.HMS.enums.PaymentStatus;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDto {

    private Long billId;

    private Long patientId;
    private Long appointmentId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDate billDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

}
