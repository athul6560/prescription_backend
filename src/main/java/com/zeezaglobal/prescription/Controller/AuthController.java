package com.zeezaglobal.prescription.Controller;

import com.zeezaglobal.prescription.DTO.UserRequest;
import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Entities.Role;
import com.zeezaglobal.prescription.Entities.User;
import com.zeezaglobal.prescription.Repository.RoleRepository;
import com.zeezaglobal.prescription.Repository.UserRepository;
import com.zeezaglobal.prescription.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Collections;

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
    public String registerUser(@RequestBody UserRequest user
                              ) {
        if (userRepository.findByUsername(user.getEmail()).isPresent()) {
            return "User already exists!";
        }

        Doctor doctor = new Doctor();
        doctor.setUsername(user.getUsername());
        doctor.setPassword(passwordEncoder.encode(user.getPassword()));
        doctor.setEmail(user.getEmail());


        userRepository.save(doctor);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}