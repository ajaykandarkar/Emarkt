package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService demoService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("{\"message\": \"" + demoService.hello() + "\"}");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = demoService.getAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/updatesalary/{salary}/{id}")
    public ResponseEntity<String> updateSalary(@PathVariable("salary") double salary, @PathVariable("id") int id) {
        try {
            demoService.updateSalary(salary, id);
            return ResponseEntity.ok("Salary updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating salary: " + e.getMessage());
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            demoService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = demoService.login(loginDto);
            return ResponseEntity.ok(new LoginResponse("success", "Login successful", token, LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("failure", "Invalid credentials", null, LocalDateTime.now().toString()));
        }
    }
}
