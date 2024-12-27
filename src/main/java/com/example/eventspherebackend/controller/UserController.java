package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.service.CustomUserDetailsService;
import com.example.eventspherebackend.service.UserService;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> register(@RequestBody Map<String, String> registrationRequest) {
        String username = registrationRequest.get("username");
        String password = registrationRequest.get("password");
        String role = registrationRequest.get("role");

        // Create a new Users object
        Users user = new Users(username, password, role); // No `id` required

        // Register the user
        userService.registerUser(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
