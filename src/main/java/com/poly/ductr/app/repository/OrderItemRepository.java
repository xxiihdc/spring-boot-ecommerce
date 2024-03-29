package com.poly.ductr.app.repository;

import com.poly.ductr.app.model.Order;
import com.poly.ductr.app.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrder(Order order);
}
