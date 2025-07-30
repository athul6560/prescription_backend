package com.zeezaglobal.prescription.Repository;

import com.zeezaglobal.prescription.Entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByDoctorIdAndPatientId(Long doctorId, Long patientId);
}