package com.poly.ductr.app.service;


import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Page<Order> findAll(Pageable pageable);

    <S extends Order> S save(S entity);

    Optional<Order> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    List<Order> findByCustomer(Customer customer);
}
