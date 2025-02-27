package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Entities.User;
import com.zeezaglobal.prescription.Repository.DoctorRepository;
import com.zeezaglobal.prescription.Repository.UserRepository;
import com.zeezaglobal.prescription.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @PostMapping("/update")
    public ResponseEntity<Doctor> createDoctor(@RequestParam Long userId,@RequestBody Doctor updatedDoctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(userId);

        if (existingDoctor.isPresent()) {
            Doctor doctor = existingDoctor.get();

            // Update fields
            doctor.setFirstName(updatedDoctor.getFirstName());
            doctor.setLastName(updatedDoctor.getLastName());
            doctor.setHospitalName(updatedDoctor.getHospitalName());
            doctor.setLicenseNumber(updatedDoctor.getLicenseNumber());

            doctor.setSpecialization(updatedDoctor.getSpecialization()); // Example field
            // Add more fields as needed

            doctorRepository.save(doctor);
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
