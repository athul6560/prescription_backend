package com.zeezaglobal.prescription.Service;

import com.zeezaglobal.prescription.DTO.PrescriptionRequestDTO;
import com.zeezaglobal.prescription.DTO.PrescriptionResponseDTO;
import com.zeezaglobal.prescription.Entities.*;
import com.zeezaglobal.prescription.Mappers.PrescriptionMapper;
import com.zeezaglobal.prescription.Repository.DoctorRepository;
import com.zeezaglobal.prescription.Repository.DrugRepository;
import com.zeezaglobal.prescription.Repository.PatientRepository;
import com.zeezaglobal.prescription.Repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DrugRepository drugRepository;

    public List<PrescriptionResponseDTO> getPrescriptionsByDoctorAndPatient(Long doctorId, Long patientId) {
        List<Prescription> prescriptions = prescriptionRepository.findByDoctorIdAndPatientId(doctorId, patientId);
        return PrescriptionMapper.toDTOList(prescriptions);
    }

    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO dto) {
        // Fetch patient and doctor (assuming they exist)
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Create prescription entity
        Prescription prescription = new Prescription();
        prescription.setPrescribedDate(dto.getPrescribedDate());
        prescription.setRemarks(dto.getRemarks());
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);

        // Convert and set prescribedDrugs
        List<PrescribedDrug> prescribedDrugs = dto.getPrescribedDrugs().stream().map(drugDTO -> {
            Drug drug = drugRepository.findById(drugDTO.getDrugId())
                    .orElseThrow(() -> new RuntimeException("Drug not found: " + drugDTO.getDrugId()));

            PrescribedDrug pd = new PrescribedDrug();
            pd.setDrug(drug);
            pd.setPrescription(prescription);
            pd.setWeight(drugDTO.getWeight());
            pd.setDosage(drugDTO.getDosage());
            pd.setFrequencyPerDay(drugDTO.getFrequencyPerDay());
            pd.setDurationDays(drugDTO.getDurationDays());
            pd.setInstructions(drugDTO.getInstructions());

            return pd;
        }).toList();

        prescription.setPrescribedDrugs(prescribedDrugs);

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return PrescriptionMapper.toDTO(savedPrescription);
    }


}