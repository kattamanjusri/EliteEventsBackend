package com.klu.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.project.model.Admin;
import com.klu.project.model.Customer;
import com.klu.project.model.Manager;
import com.klu.project.dao.AdminDao;
import com.klu.project.dao.CustomerDao;
import com.klu.project.dao.ManagerDao;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private CustomerDao customerDao;

    // Admin Login
    public Admin checkadminlogin(String username, String password) {
        return adminDao.findByUsernameAndPassword(username, password);
    }

    // Save new Admin
    public String saveAdmin(Admin admin) {
        try {
            adminDao.save(admin);
            return "Admin registered successfully";
        } catch (Exception e) {
            return "Error registering admin: " + e.getMessage();
        }
    }

    // Add new Event Manager
    public String addeventmanager(Manager manager) {
        managerDao.save(manager);
        return "Event Manager Added Successfully";
    }

    // Get all Event Managers
    public List<Manager> displayeventmanagers() {
        return managerDao.findAll();
    }

    // Get all Customers
    public List<Customer> displaycustomers() {
        return customerDao.findAll();
    }

    // Delete a Customer by ID
    public String deletecustomer(int cid) {
        Optional<Customer> customer = customerDao.findById(cid);
        if (customer.isPresent()) {
            customerDao.deleteById(cid);
            return "Customer Deleted Successfully";
        } else {
            return "Customer ID Not Found";
        }
    }

    // Delete a Manager by ID
    public String deletemanager(int nid) {
        managerDao.deleteById(nid);
        return "Manager Deleted Successfully";
    }

    // Get Manager by ID
    public Manager getManagerById(int id) {
        return managerDao.findById(id).orElse(null);
    }

    // Update Manager info
    public Manager updateManager(Manager manager) {
        return managerDao.save(manager);
    }
}
