package com.project.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.User;
import com.project.service.UserService;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.OPTIONS }, allowCredentials = "true")
@RestController
@RequestMapping("/api/admin/users") // Adjusted base path for admin users
public class AdminUserController {

    @Autowired
    private UserService userdemoService; // Using the new service

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() { // Using the new entity
        return new ResponseEntity<>(userdemoService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{usern}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String usern) { // Using the new entity
        Optional<User> user = userdemoService.getUserByUsername(usern);
        return user.map(response -> ResponseEntity.ok().body(Optional.of(response)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{usern}")
    public ResponseEntity<String> updateUserRole(@PathVariable String usern, @RequestBody Map<String, String> payload) {
        String newRole = payload.get("role");
        if (newRole == null || newRole.trim().isEmpty()) {
            return new ResponseEntity<>("New role cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        String result = userdemoService.updateUserRole(usern, newRole);
        if (result.startsWith("Role")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}