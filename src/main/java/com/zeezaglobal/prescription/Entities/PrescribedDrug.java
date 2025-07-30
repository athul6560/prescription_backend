package com.zeezaglobal.prescription.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescribedDrug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double weight; // e.g., 250mg
    private String dosage; // e.g., "1 tablet"
    private int frequencyPerDay; // e.g., 3 times a day
    private int durationDays; // e.g., for 5 days
    private String instructions; // e.g., "After food"

    @ManyToOne
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;
}
