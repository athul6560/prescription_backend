package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.Entities.Drug;
import com.zeezaglobal.prescription.Service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/drugs")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @PostMapping("/add")
    public ResponseEntity<Drug> addDrug(@RequestBody Drug drug) {
        return ResponseEntity.ok(drugService.saveDrug(drug));
    }

    @GetMapping
    public ResponseEntity<List<Drug>> getAllDrugs() {
        return ResponseEntity.ok(drugService.getAllDrugs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Drug>> getDrugById(@PathVariable Long id) {
        return ResponseEntity.ok(drugService.getDrugById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return ResponseEntity.noContent().build();
    }
}
