package com.zeezaglobal.prescription.Controller;

import com.stripe.exception.StripeException;
import com.zeezaglobal.prescription.DTO.LoginDTO;
import com.zeezaglobal.prescription.DTO.UserRequest;
import com.zeezaglobal.prescription.Entities.Doctor;
import com.zeezaglobal.prescription.Entities.Role;
import com.zeezaglobal.prescription.Entities.User;
import com.zeezaglobal.prescription.Repository.RoleRepository;
import com.zeezaglobal.prescription.Repository.UserRepository;
import com.zeezaglobal.prescription.Service.StripeService;
import com.zeezaglobal.prescription.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    private StripeService stripeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserRequest user) throws StripeException {
        Map<String, String> response = new HashMap<>();

        // Check if the user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            response.put("message", "User already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Create a new doctor and set values
        Doctor doctor = new Doctor();
        doctor.setUsername(user.getUsername());
        try {
            // Attempt to create a Stripe customer
            String customerId = stripeService.createCustomer(user.getUsername());
            doctor.setStripeUsername(customerId); // Save Stripe customer ID if needed
        } catch (StripeException e) {
            response.put("message", "Registration not complete. Please contact support.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        doctor.setPassword(passwordEncoder.encode(user.getPassword()));


        // Save the new doctor
        userRepository.save(doctor);

        // Success response
        response.put("message", "User registered successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginDTO user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email or password cannot be null"));
            }

            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            // Load user details
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // Fetch user entity from DB
            User loggedInUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            // Prepare response with token and user details
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", loggedInUser.getId());
            userMap.put("username", loggedInUser.getUsername());
           // userMap.put("roles", loggedInUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

            // Check if user is a Doctor and add validated field
            if (loggedInUser instanceof Doctor) {
                Doctor doctor = (Doctor) loggedInUser;
                userMap.put("isValidated", doctor.getValidated());
            } else {
                userMap.put("isValidated", null); // or false if needed
            }

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userMap);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An error occurred while processing your request"));
        }
    }
}