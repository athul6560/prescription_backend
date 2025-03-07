package com.zeezaglobal.prescription.Service;

import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Entities.Patient;
import com.zeezaglobal.prescription.Repository.DrugRepository;
import com.zeezaglobal.prescription.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    public Page<Patient> getPatientsByDoctorId(Long doctorId, Pageable pageable) {
        return patientRepository.findByDoctorId(doctorId, pageable);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
