package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.entity.User;
import com.project.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/users")
public class Usercontroller {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String result = userService.createUser(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(response -> ResponseEntity.ok().body(Optional.of(response)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        String result = userService.updateUser(user);
        if (result.startsWith("User updated")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        String result = userService.deleteUser(username);
        if (result.startsWith("User deleted")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String usern = credentials.get("usern");
        String pwd = credentials.get("pwd");

        User user = userService.findByUsern(usern);

        if (user != null && pwd.equals(user.getPwd())) { // WARNING: Plain-text password check
            return ResponseEntity.ok(Map.of(
                    "usern", user.getUsern(),
                    "email", user.getEmail(),
                    "role", user.getRole(),
                    "phone", user.getPhone()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/role/{username}")
    public ResponseEntity<String> updateUserRole(@PathVariable String username, @RequestBody Map<String, String> payload) {
        String newRole = payload.get("role");
        if (newRole == null || newRole.trim().isEmpty()) {
            return new ResponseEntity<>("New role cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        String result = userService.updateUserRole(username, newRole);
        if (result.startsWith("Role")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String usern) {
        User user = userService.findByUsern(usern);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> retriveUser(@RequestBody Map<String, String> credentials) {
        String usern = credentials.get("usern");
        String pwd = credentials.get("pwd");
        User user = userService.findByUsern(usern);

        if (user != null && user.getPwd().equals(pwd)) { // WARNING: Plain-text password check
            return ResponseEntity.ok(Map.of("role", user.getRole()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}