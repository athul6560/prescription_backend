package com.zeezaglobal.prescription.DTO;

import com.zeezaglobal.prescription.Entities.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private Long id;
    private LocalDate prescribedDate;
    private String remarks;
    private Long patientId;
    private Long doctorId;
    private List<Long> drugIds;
}
