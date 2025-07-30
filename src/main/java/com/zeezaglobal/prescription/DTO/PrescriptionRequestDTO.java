package com.zeezaglobal.prescription.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
public class PrescriptionRequestDTO {
    private LocalDate prescribedDate;
    private String remarks;
    private Long doctorId;
    private Long patientId;
    private List<PrescribedDrugDTO> prescribedDrugs;

    @Data
    public static class PrescribedDrugDTO {
        private Long drugId;
        private double weight;
        private String dosage;
        private int frequencyPerDay;
        private int durationDays;
        private String instructions;
    }
}