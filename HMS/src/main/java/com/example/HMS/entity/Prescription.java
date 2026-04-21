package com.example.HMS.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    private String medicine;
    private String dosage;
    private String instructions;

    @OneToOne(mappedBy = "prescription", orphanRemoval = true)
    private MedicalRecord medicalRecord;
}
