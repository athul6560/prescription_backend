package com.zeezaglobal.prescription.Service;

import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Repository.DoctorRepository;
import com.zeezaglobal.prescription.Repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    public Optional<Drug> getDrugById(Long id) {
        return drugRepository.findById(id);
    }

    public void deleteDrug(Long id) {
        drugRepository.deleteById(id);
    }
}
