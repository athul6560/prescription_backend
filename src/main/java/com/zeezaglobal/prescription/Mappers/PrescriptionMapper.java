package com.zeezaglobal.prescription.Mappers;

import com.zeezaglobal.prescription.DTO.DrugDTO;
import com.zeezaglobal.prescription.DTO.PrescriptionDTO;
import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Entities.Prescription;

import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionMapper {

    public static PrescriptionDTO toDTO(Prescription prescription) {
        List<Drug> drugs = prescription.getDrugs();

        return new PrescriptionDTO(
                prescription.getId(),
                prescription.getPrescribedDate(),
                prescription.getRemarks(),
                prescription.getPatient().getId(),
                prescription.getDoctor().getId(),
                drugs.stream().map(PrescriptionMapper::toDrugDTO).collect(Collectors.toList()),
                drugs.stream().map(Drug::getId).collect(Collectors.toList()) // drugIds
        );
    }

    private static DrugDTO toDrugDTO(Drug drug) {
        return new DrugDTO(
                drug.getId(),
                drug.getSerialNumber(),
                drug.getType(),
                drug.getName(),
                drug.getDescription(),
                drug.getType_name(),
                drug.getForm()
        );
    }
}