package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.DTO.LoginDTO;
import com.zeezaglobal.prescription.DTO.UserRequest;
import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Entities.Role;
import com.zeezaglobal.prescription.Entities.User;
import com.zeezaglobal.prescription.Repository.RoleRepository;
import com.zeezaglobal.prescription.Repository.UserRepository;
import com.zeezaglobal.prescription.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserRequest user) {
        Map<String, String> response = new HashMap<>();

        // Check if the user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            response.put("message", "User already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Create a new doctor and set values
        Doctor doctor = new Doctor();
        doctor.setUsername(user.getUsername());
        doctor.setPassword(passwordEncoder.encode(user.getPassword()));
        doctor.setEmail(user.getEmail());

        // Save the new doctor
        userRepository.save(doctor);

        // Success response
        response.put("message", "User registered successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginDTO user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}