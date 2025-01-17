package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.PortfolioDTO;
import com.example.eventspherebackend.dto.UsersDTO;
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
@RequestMapping("/user")
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
        try {
            String username = users.getUsername();
            String password = users.getPassword();

            // Authenticate the user and generate a token
            String token = userService.verifyUser(username, password);
            if ("bad credentials".equals(token) || "fail".equals(token)) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
            }

            // Return the generated token
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during login: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody UsersDTO usersDTO) {
        try {
            // Register the user
            userService.registerUser(usersDTO);

            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UsersDTO usersDTO) {
        try {
            // Update the user
            userService.updateUser(usersDTO);

            return ResponseEntity.ok(Map.of("message", "User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> remove(@RequestParam int userId) {
        try {
            // Remove the user
            userService.removeUser(String.valueOf(userId));

            return ResponseEntity.ok(Map.of("message", "User removed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing user: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER')")
    public ResponseEntity<?> getAll() {
        try {
            // Get all users
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/getById")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER')")
    public ResponseEntity<?> getById(@RequestParam int userId) {
        try {
            // Get user by ID
            return ResponseEntity.ok(userService.getUserById(String.valueOf(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user by ID: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Invalid token");
            }
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            jwtUtil.blacklistToken(token);
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during logout: " + e.getMessage());
        }
    }

    @GetMapping("/message")
    public ResponseEntity<?> getMessage() {
        try {
            return ResponseEntity.ok("Hello, this is a simple message");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching message: " + e.getMessage());
        }
    }

    @PutMapping("/updatePortfolio")
    public ResponseEntity<?> updatePortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        try {
            userService.updatePortfolio(portfolioDTO);
            return ResponseEntity.ok(Map.of("message", "Portfolio updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating portfolio: " + e.getMessage());
        }
    }

    @GetMapping("/getPortfolioByUserId")
    public ResponseEntity<?> getPortfolioByUserId(@RequestParam int userId) {
        try {
            return ResponseEntity.ok(userService.getPortfolioByStudentId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching portfolio: " + e.getMessage());
        }
    }

    @GetMapping("/getByRole")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER')")
    public ResponseEntity<?> getByRole(@RequestParam String role) {
        try {
            return ResponseEntity.ok(userService.getUserByRole(role));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching users by role: " + e.getMessage());
        }
    }
}