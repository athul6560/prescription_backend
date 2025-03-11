package com.zeezaglobal.prescription.Service;

import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Entities.Patient;
import com.zeezaglobal.prescription.Entities.Prescription;
import com.zeezaglobal.prescription.Repository.DrugRepository;
import com.zeezaglobal.prescription.Repository.PatientRepository;
import com.zeezaglobal.prescription.Repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Transactional
    public Prescription createPrescription(Long patientId, List<Long> drugIds, String remarks) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        List<Drug> drugs = drugRepository.findAllById(drugIds);
        if (drugs.isEmpty()) {
            throw new RuntimeException("No valid drugs found");
        }

        Prescription prescription = new Prescription();
        prescription.setPatient(patientOpt.get());
        prescription.setDrugs(drugs);
        prescription.setPrescribedDate(java.time.LocalDate.now());
        prescription.setRemarks(remarks);

        return prescriptionRepository.save(prescription);
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
