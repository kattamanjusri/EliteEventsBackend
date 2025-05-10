package com.klu.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klu.project.model.Customer;
import com.klu.project.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*") // * means any URL
public class CustomerController 
{
   @Autowired
   private CustomerService customerService;
	
   @GetMapping("/")
   public String home()
   {
	   return "FSD SDP Project";
   }
   
   @PostMapping("/registration")
   public ResponseEntity<String> customerregistration(@RequestBody Customer customer)
   {
	   try
	   {
		  String output = customerService.customerregistration(customer);
		  return ResponseEntity.ok(output); // 200 - success
	   }
	   catch(Exception e)
	   {
		  // return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
		   return ResponseEntity.status(500).body("Cusomter Registration failed...");
	   }
   }
   
   @PostMapping("/checkcustomerlogin")
   public ResponseEntity<?> checkcustomerlogin(@RequestBody Customer customer) 
   {
       try 
       {
           Customer c = customerService.checkcustomerlogin(customer.getUsername(), customer.getPassword());

           if (c!=null) 
           {
               return ResponseEntity.ok(c); // if login is successful
           } 
           else 
           {
               return ResponseEntity.status(401).body("Invalid Username or Password"); // if login is fail
           }
       } 
       catch (Exception e) 
       {
           return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
       }
   }
  
   @GetMapping("/details/username/{username}")
   public ResponseEntity<Customer> getCustomerDetailsByUsername(@PathVariable String username) {
       try {
           Customer customer = customerService.getCustomerByUsername(username);
           if (customer != null) {
               return ResponseEntity.ok(customer);
           } else {
               return ResponseEntity.status(404).body(null); // Customer not found
           }
       } catch (Exception e) {
           return ResponseEntity.status(500).body(null); // Internal server error
       }
   }
   
   @PutMapping("/update/username/{username}")
   public ResponseEntity<String> updateCustomerDetails(@PathVariable String username, @RequestBody Customer updatedCustomer) {
       try {
           Customer existingCustomer = customerService.getCustomerByUsername(username);

           if (existingCustomer != null) {
               // Update fields except username
               existingCustomer.setName(updatedCustomer.getName());
               existingCustomer.setDob(updatedCustomer.getDob());
               existingCustomer.setEmail(updatedCustomer.getEmail());
               existingCustomer.setMobileno(updatedCustomer.getMobileno());
               existingCustomer.setLocation(updatedCustomer.getLocation());
               existingCustomer.setPassword(updatedCustomer.getPassword());

               customerService.updateCustomer(existingCustomer); // Call service to save updates
               return ResponseEntity.ok("Customer details updated successfully");
           } else {
               return ResponseEntity.status(404).body("Customer not found");
           }
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Update failed: " + e.getMessage());
       }
   }


}