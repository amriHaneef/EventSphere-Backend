package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.service.CustomUserDetailsService;
import com.example.eventspherebackend.service.UserService;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        String username = users.getUsername();
        String password = users.getPassword();

        // Authenticate the user and generate a token
        String token = userService.verifyUser(username, password);
        if ("bad credentials".equals(token) || "fail".equals(token)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }

        // Return the generated token
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registrationRequest) {
        try {
            String username = registrationRequest.get("username");
            String password = registrationRequest.get("password");
            String role = registrationRequest.get("role");
            String name = registrationRequest.get("name");

            // Create a new Users object
            Users user = new Users(username, password, role,name); // No `id` required

            // Register the user
            userService.registerUser(user);

            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        }
        catch (Exception e){
            return ResponseEntity.ok(Map.of("error: ", e.getMessage()));

        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "User logged out successfully"));
    }

    @GetMapping("/message")
    public String getMessage() {
        return ("Hello, this is a simple message");
    }


}
