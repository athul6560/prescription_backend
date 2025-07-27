package com.zeezaglobal.prescription.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugDTO {
    private Long id;
    private int serialNumber;
    private String type;
    private String name;
    private String description;
    private String type_name;
    private String form;
}
