package com.klu.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private int managerId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(length = 20, nullable = false)
    private String dob;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String role;
    
    @Column(length = 20, nullable = false, unique = true)
    private String mobileno;

    @Column(length = 50, nullable = false)
    private String company_name;

    @Column(length = 50, nullable = false)
    private String company_location;

    // Getters and Setters

    public int getmanagerId() {
        return managerId;
    }

    public void setId(int managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_location() {
        return company_location;
    }

    public void setCompany_location(String company_location) {
        this.company_location = company_location;
    }
}
