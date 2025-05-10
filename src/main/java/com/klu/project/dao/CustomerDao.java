package com.klu.project.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.project.model.Customer;


@Repository
public interface CustomerDao extends JpaRepository<Customer,Integer>
{
  public Customer findByUsernameAndPassword(String username, String password);
  public Customer findByUsername(String username);


}
