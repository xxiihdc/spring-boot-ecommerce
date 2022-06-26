package com.poly.ductr.app.serviceimpl;


import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.model.Order;
import com.poly.ductr.app.repository.OrderRepository;
import com.poly.ductr.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {
   @Autowired
    OrderRepository repository;

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public <S extends Order> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public List<Order> findByCustomer(Customer customer) {
       return this.repository.findByCustomer(customer);
    }
}
