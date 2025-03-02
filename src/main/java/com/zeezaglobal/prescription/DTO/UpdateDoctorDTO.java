package com.zeezaglobal.prescription.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String licenseNumber;
    private String hospitalName;
    private String contactNumber;
}