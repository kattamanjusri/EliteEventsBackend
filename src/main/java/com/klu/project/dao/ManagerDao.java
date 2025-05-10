package com.klu.project.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.project.model.Manager;


@Repository
public interface ManagerDao extends JpaRepository<Manager,Integer>
{
  public Manager findByUsernameAndPassword(String username, String password);
  Optional<Manager> findByRole(String role);
  Manager findByName(String name);
}
