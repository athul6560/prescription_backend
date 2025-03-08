package com.zeezaglobal.prescription.Repository;

import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByDoctorId(Long doctorId, Pageable pageable);
}
