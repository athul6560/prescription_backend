package com.zeezaglobal.prescription.DTO;

import com.zeezaglobal.prescription.Entities.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private Long id;

    private LocalDate prescribedDate;
    private String remarks;


    private Long patient_id;

}
