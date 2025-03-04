package com.zeezaglobal.prescription.Repository;

import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
