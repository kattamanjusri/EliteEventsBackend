package com.klu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klu.project.model.Customer;
import com.klu.project.dao.CustomerDao;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerRepository;

    // Method for registering a customer
    public String customerregistration(Customer customer) {
        customerRepository.save(customer);
        return "Customer Registered Successfully";
    }

    // Method for checking customer login
    public Customer checkcustomerlogin(String username, String password) {
        return customerRepository.findByUsernameAndPassword(username, password);
    }
 // Method to get customer by ID
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
    public Customer findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    // Method to update customer
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
   
}
