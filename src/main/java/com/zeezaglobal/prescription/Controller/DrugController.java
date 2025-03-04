package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drug")
public class DrugController {
    @Autowired
    private DrugService drugService;

    @PostMapping("/add")
    public ResponseEntity<Drug> addDrug(@RequestBody Drug drug) {
        Drug savedDrug = drugService.saveDrug(drug);
        return ResponseEntity.ok(savedDrug);
    }
}
