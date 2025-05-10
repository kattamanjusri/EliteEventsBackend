package com.klu.project.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.project.model.Event;
import com.klu.project.model.Manager;
import com.klu.project.dao.EventDao;
import com.klu.project.dao.ManagerDao;

@Service
public class ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private EventDao eventDao;

    // Check manager login credentials
    public Manager checkmanagerlogin(String username, String password) {
        return managerDao.findByUsernameAndPassword(username, password);
    }

    // Add new manager with ID check
    public String addManager(Manager manager) {
        if (managerDao.existsById(manager.getmanagerId())) {
            return "Manager with ID " + manager.getmanagerId() + " already exists!";
        }
        managerDao.save(manager);
        return "Manager added successfully!";
    }

    // Save manager directly (can be used for update as well)
    public void save(Manager manager) {
        managerDao.save(manager);
    }

    // Get manager by ID
    public List<Event> getEventsByManagerId(int managerId) {
        return eventDao.findByManagerId(managerId);
    }
    public Manager getManagerByRole(String role) {
        Optional<Manager> manager = managerDao.findByRole(role);
        return manager.orElse(null); // Return null if no manager is found
    }
    public Manager assignManagerToEvent(Event event) {
        String eventName = event.getEventName().toLowerCase();  // Get the event type in lowercase

        Manager manager = null;

        // Assign the manager based on the event name or type
        if (eventName.contains("wedding")) {
            Optional<Manager> managerOptional = managerDao.findByRole("💍 Wedding Manager");
            manager = managerOptional.orElse(null);  // If no manager is found, set it to null
        } else if (eventName.contains("bachelor")) {
            Optional<Manager> managerOptional = managerDao.findByRole("🎉 Bachelor Party Host");
            manager = managerOptional.orElse(null);
        } else if (eventName.contains("birthday")) {
            Optional<Manager> managerOptional = managerDao.findByRole("🎂 Birthday Planner");
            manager = managerOptional.orElse(null);
        } else if (eventName.contains("sangeet")) {
            Optional<Manager> managerOptional = managerDao.findByRole("🎶 Sangeet Coordinator");
            manager = managerOptional.orElse(null);
        } else if (eventName.contains("haldi")) {
            Optional<Manager> managerOptional = managerDao.findByRole("🌼 Haldi Organizer");
            manager = managerOptional.orElse(null);
        } else if (eventName.contains("engagement")) {
            Optional<Manager> managerOptional = managerDao.findByRole("💑 Engagement Specialist");
            manager = managerOptional.orElse(null);
        }

        return manager;  // Return the assigned manager, or null if none found
    }
    
    public Manager getManagerByName(String name) {
    	 System.out.println("Fetching manager with name: " + name);
        return managerDao.findByName(name);  // Assuming `findByName` is a method in ManagerDao
    }
}
