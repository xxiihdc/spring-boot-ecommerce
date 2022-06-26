package com.poly.ductr.app.repository;

import com.poly.ductr.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public List<Customer> findByEmail(String email);
    public List<Customer> findByResetPassword(String token);
}
