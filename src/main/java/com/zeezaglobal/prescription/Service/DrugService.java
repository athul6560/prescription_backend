package com.zeezaglobal.prescription.Service;

import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Repository.DoctorRepository;
import com.zeezaglobal.prescription.Repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService {
    @Autowired
    private DrugRepository drugRepository;
    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
    }
}
