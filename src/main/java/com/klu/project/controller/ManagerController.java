package com.klu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.project.model.Event;
import com.klu.project.model.Manager;
import com.klu.project.service.EventService;
import com.klu.project.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*")
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private EventService eventService;

    // Check manager login
    @PostMapping("/checkmanagerlogin")
    public ResponseEntity<?> checkmanagerlogin(@RequestBody Manager manager) {
        try {
            Manager m = managerService.checkmanagerlogin(manager.getUsername(), manager.getPassword());
            if (m != null) {
                return ResponseEntity.ok(m); // if login is successful
            } else {
                return ResponseEntity.status(401).body("Invalid Username or Password"); // if login fails
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    // Get manager by category
    @GetMapping("/role/{role}")
    public ResponseEntity<?> getManagerByRole(@PathVariable String role) {
        try {
            Manager manager = managerService.getManagerByRole(role);
            if (manager != null) {
                return ResponseEntity.ok(manager);
            } else {
                return ResponseEntity.status(404).body("No manager found for role: " + role);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching manager: " + e.getMessage());
        }
    }
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Event>> getEventsByManagerId(@PathVariable int managerId) {
        List<Event> events = eventService.getEventsByManagerId(managerId);
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no events
        }
        return ResponseEntity.ok(events);
    }

}
