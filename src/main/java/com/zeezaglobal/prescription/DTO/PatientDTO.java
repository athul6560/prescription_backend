package com.zeezaglobal.prescription.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long id;
    private Long numberOfVisit;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String medicalHistory;
    private Long doctor_id;
}
