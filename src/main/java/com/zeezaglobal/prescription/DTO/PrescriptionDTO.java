package com.zeezaglobal.prescription.DTO;

import com.zeezaglobal.prescription.Entities.Patient;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private Long id;
    private LocalDate prescribedDate;
    private String remarks;
    private Long patientId;
    private Long doctorId;
    private List<DrugDTO> drugs;
    private List<Long> drugIds;


}
