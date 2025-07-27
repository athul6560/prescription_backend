package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.DTO.PrescriptionDTO;
import com.zeezaglobal.prescription.Entities.Prescription;
import com.zeezaglobal.prescription.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Map<String, Object> payload) {
        Long patientId = ((Number) payload.get("patientId")).longValue();
        List<Integer> drugIdsInt = (List<Integer>) payload.get("drugIds");
        List<Long> drugIds = drugIdsInt.stream().map(Integer::longValue).toList();
        String remarks = (String) payload.get("remarks");
        PrescriptionDTO prescriptionInput = new PrescriptionDTO();
        prescriptionInput.setPatientId(patientId);
        prescriptionInput.setDrugIds(drugIds);
        prescriptionInput.setRemarks(remarks);
        PrescriptionDTO prescription = prescriptionService.createPrescription(prescriptionInput);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Prescription>> getPrescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
