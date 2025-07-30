package com.zeezaglobal.prescription.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionResponseDTO {
    private Long id;
    private String remarks;
    private LocalDate prescribedDate;
    private String doctorName;
    private String patientName;
    private List<PrescribedDrugDTO> prescribedDrugs;

    @Data
    public static class PrescribedDrugDTO {
        private String drugName;
        private String form;
        private double weight;
        private String dosage;
        private int frequencyPerDay;
        private int durationDays;
        private String instructions;
    }
}
