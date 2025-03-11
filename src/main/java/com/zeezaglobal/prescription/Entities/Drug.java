package com.zeezaglobal.prescription.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int serialNumber;
    private String type;
    private String name;
    private String dose;
    private String frequency;
    private String duration;
    private String remarks;

    @ManyToMany(mappedBy = "drugs")
    @JsonBackReference
    private List<Prescription> prescriptions;
}
