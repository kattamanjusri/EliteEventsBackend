package com.klu.project.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.project.model.Admin;

public interface AdminDao extends JpaRepository<Admin, String> {
    Admin findByUsernameAndPassword(String username, String password);
}
