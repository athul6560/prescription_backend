package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.DTO.UpdateDoctorDTO;
import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Repository.DoctorRepository;
import com.zeezaglobal.prescription.Repository.UserRepository;
import com.zeezaglobal.prescription.Service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/getdata/{doctorId}")
    public ResponseEntity<Doctor> getDoctorDetails(@PathVariable Long doctorId) {
        return doctorRepository.findById(doctorId)
                .map(ResponseEntity::ok) // If doctor exists, return 200 OK with the doctor data
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // If not found, return 404
    }

    @PostMapping("/update")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody UpdateDoctorDTO updatedDoctor) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Updating doctor with ID: {}", updatedDoctor.getId());

        if (updatedDoctor.getId() == null) {
            logger.error("Doctor ID is null in update request.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<Doctor> existingDoctor = doctorRepository.findById(updatedDoctor.getId());

        if (existingDoctor.isPresent()) {
            Doctor doctor = existingDoctor.get();

            // Log old and new values
            logger.info("Old values - FirstName: {}, LastName: {}, Hospital: {}",
                    doctor.getFirstName(), doctor.getLastName(), doctor.getHospitalName());
            logger.info("New values - FirstName: {}, LastName: {}, Hospital: {}",
                    updatedDoctor.getFirstName(), updatedDoctor.getLastName(), updatedDoctor.getHospitalName());

            // Update fields
            doctor.setFirstName(updatedDoctor.getFirstName());
            doctor.setLastName(updatedDoctor.getLastName());
            doctor.setHospitalName(updatedDoctor.getHospitalName());
            doctor.setLicenseNumber(updatedDoctor.getLicenseNumber());
            doctor.setContactNumber(updatedDoctor.getContactNumber());
            doctor.setSpecialization(updatedDoctor.getSpecialization());

            Doctor savedDoctor = doctorRepository.save(doctor);
            logger.info("Doctor updated successfully with ID: {}", savedDoctor.getId());

            return ResponseEntity.ok(savedDoctor);
        } else {
            logger.error("Doctor not found with ID: {}", updatedDoctor.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




}
