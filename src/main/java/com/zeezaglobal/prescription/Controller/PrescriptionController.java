package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.DTO.PrescriptionRequestDTO;
import com.zeezaglobal.prescription.DTO.PrescriptionResponseDTO;
import com.zeezaglobal.prescription.Entities.Prescription;
import com.zeezaglobal.prescription.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
//GET /api/prescriptions/filter?doctorId=1&patientId=2
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/filter")
    public List<PrescriptionResponseDTO> getPrescriptionsByDoctorAndPatient(
            @RequestParam Long doctorId,
            @RequestParam Long patientId) {
        return prescriptionService.getPrescriptionsByDoctorAndPatient(doctorId, patientId);
    }

    @PostMapping
    public PrescriptionResponseDTO createPrescription(@RequestBody PrescriptionRequestDTO dto) {
        return prescriptionService.createPrescription(dto);
    }


}
