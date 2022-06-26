package com.poly.ductr.app.service;

import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerService{
    Optional<Customer> findById(Integer id);
    Customer findByEmail(String email);

    <S extends Customer> S save(S entity);

    List<Customer> findByResetPassword(String token);
}
