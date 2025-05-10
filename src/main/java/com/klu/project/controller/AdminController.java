package com.klu.project.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.klu.project.model.Admin;
import com.klu.project.model.Customer;
import com.klu.project.model.Manager;
import com.klu.project.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/registeradmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        String response = adminService.saveAdmin(admin);
        if (response.equals("Admin registered successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    // Admin Login
    @PostMapping("/checkadminlogin")
    public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin) {
        try {
            Admin a = adminService.checkadminlogin(admin.getUsername(), admin.getPassword());

            if (a != null) {
                return ResponseEntity.ok(a); // Login success
            } else {
                return ResponseEntity.status(401).body("Invalid Username or Password");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    // View All Customers
    @GetMapping("/allcustomers")
    public ResponseEntity<List<Customer>> viewallcustomers() {
        List<Customer> customers = adminService.displaycustomers();
        return ResponseEntity.ok(customers); // 200 - success
    }

    // Add Event Manager
    @PostMapping("/addmanager")
    public ResponseEntity<String> addeventmanager(@RequestBody Manager manager) {
        try {
            String output = adminService.addeventmanager(manager);
            return ResponseEntity.ok(output); // 200 - success
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Add Event Manager: " + e.getMessage());
        }
    }

    // View All Event Managers
    @GetMapping("/allmanagers")
    public ResponseEntity<List<Manager>> viewalleventmanagers() {
        List<Manager> eventManagers = adminService.displayeventmanagers();
        return ResponseEntity.ok(eventManagers); // 200 - success
    }

    // Delete Customer
    @DeleteMapping("/deletecustomer")
    public ResponseEntity<String> deletecustomer(@RequestParam int cid) {
        try {
            String output = adminService.deletecustomer(cid);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Delete Customer: " + e.getMessage());
        }
    }

    // Delete Event Manager
    @DeleteMapping("/deletemanager/{nid}")
    public ResponseEntity<String> deletemanager(@PathVariable int nid) {
        try {
            String output = adminService.deletemanager(nid); // Make sure this is correctly calling the service method
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Delete Manager: " + e.getMessage());
        }
    }
    @PutMapping("/updatemanager/{id}")
    public ResponseEntity<?> updateManager(@PathVariable int id, @RequestBody Manager updatedManager) {
        try {
            Manager existingManager = adminService.getManagerById(id);
            if (existingManager != null) {
                existingManager.setName(updatedManager.getName());
                existingManager.setUsername(updatedManager.getUsername());
                existingManager.setEmail(updatedManager.getEmail());
                existingManager.setDob(updatedManager.getDob());
                existingManager.setMobileno(updatedManager.getMobileno());
                existingManager.setGender(updatedManager.getGender());
                existingManager.setPassword(updatedManager.getPassword());
                existingManager.setRole(updatedManager.getRole());
                existingManager.setCompany_location(updatedManager.getCompany_location());
                existingManager.setCompany_name(updatedManager.getCompany_name());

                Manager savedManager = adminService.updateManager(existingManager);
                return ResponseEntity.ok(savedManager);
            } else {
                return ResponseEntity.status(404).body("Manager not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update Manager: " + e.getMessage());
        }
    }

    
}
