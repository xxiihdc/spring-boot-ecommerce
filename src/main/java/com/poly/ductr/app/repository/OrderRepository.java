package com.poly.ductr.app.repository;


import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCustomer(Customer customer);
}
