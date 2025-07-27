package com.zeezaglobal.prescription.Service;

import com.zeezaglobal.prescription.DTO.PrescriptionDTO;
import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Entities.Patient;
import com.zeezaglobal.prescription.Entities.Prescription;
import com.zeezaglobal.prescription.Mappers.PrescriptionMapper;
import com.zeezaglobal.prescription.Repository.DrugRepository;
import com.zeezaglobal.prescription.Repository.PatientRepository;
import com.zeezaglobal.prescription.Repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DrugRepository drugRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               PatientRepository patientRepository,
                               DrugRepository drugRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.drugRepository = drugRepository;
    }

    public PrescriptionDTO createPrescription(PrescriptionDTO dto) {
        // Fetch patient
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Fetch drugs
        List<Drug> drugs = drugRepository.findAllById(dto.getDrugIds());
        if (drugs.isEmpty()) {
            throw new RuntimeException("No valid drugs found");
        }

        // Create prescription
        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setDrugs(drugs);
        prescription.setPrescribedDate(dto.getPrescribedDate() != null ? dto.getPrescribedDate() : java.time.LocalDate.now());
        prescription.setRemarks(dto.getRemarks());

        // Set doctor from patient
        prescription.setDoctor(patient.getDoctor());

        // Save and return DTO using the updated mapper
        Prescription saved = prescriptionRepository.save(prescription);
        return PrescriptionMapper.toDTO(saved);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }


}
