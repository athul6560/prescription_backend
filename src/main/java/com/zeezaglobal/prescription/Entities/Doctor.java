package com.zeezaglobal.prescription.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends User {
    private String firstName;
    private String lastName;
    private String specialization;
    private String licenseNumber;
    private String hospitalName;

    @Column(nullable = false, unique = true)
    private String contactNumber;
}