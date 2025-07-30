package com.zeezaglobal.prescription.Mappers;

import com.zeezaglobal.prescription.DTO.PrescriptionResponseDTO;
import com.zeezaglobal.prescription.Entities.PrescribedDrug;
import com.zeezaglobal.prescription.Entities.Prescription;

import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionMapper {

    public static PrescriptionResponseDTO toDTO(Prescription prescription) {
        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();
        dto.setId(prescription.getId());
        dto.setPrescribedDate(prescription.getPrescribedDate());
        dto.setRemarks(prescription.getRemarks());
        dto.setDoctorName(prescription.getDoctor().getFirstName());
        dto.setPatientName(prescription.getPatient().getFirstName());

        List<PrescriptionResponseDTO.PrescribedDrugDTO> drugDTOs = prescription.getPrescribedDrugs()
                .stream()
                .map(PrescriptionMapper::mapPrescribedDrug)
                .collect(Collectors.toList());

        dto.setPrescribedDrugs(drugDTOs);

        return dto;
    }

    private static PrescriptionResponseDTO.PrescribedDrugDTO mapPrescribedDrug(PrescribedDrug pd) {
        PrescriptionResponseDTO.PrescribedDrugDTO dto = new PrescriptionResponseDTO.PrescribedDrugDTO();
        dto.setDrugName(pd.getDrug().getName());
        dto.setForm(pd.getDrug().getForm());
        dto.setDosage(pd.getDosage());
        dto.setWeight(pd.getWeight());
        dto.setFrequencyPerDay(pd.getFrequencyPerDay());
        dto.setDurationDays(pd.getDurationDays());
        dto.setInstructions(pd.getInstructions());
        return dto;
    }

    public static List<PrescriptionResponseDTO> toDTOList(List<Prescription> prescriptions) {
        return prescriptions.stream().map(PrescriptionMapper::toDTO).collect(Collectors.toList());
    }
}
