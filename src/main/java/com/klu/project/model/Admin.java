package com.klu.project.model;
import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(length = 50)
    private String username;

    @Column(length = 50)
    private String password;

    // Getters and setters
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
}
