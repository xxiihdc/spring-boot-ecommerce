package com.poly.ductr.app.serviceimpl;

import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.repository.CustomerRepository;
import com.poly.ductr.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository repository;
    @Override
    public Optional<Customer> findById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public Customer findByEmail(String email) {
        List<Customer> lst = repository.findByEmail(email);
        if (lst.isEmpty()) return null;
        return lst.get(0);
    }
    @Override
    public <S extends Customer> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public List<Customer> findByResetPassword(String token) {
        return repository.findByResetPassword(token);
    }
}
