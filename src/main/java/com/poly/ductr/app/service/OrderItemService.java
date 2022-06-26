package com.poly.ductr.app.service;

import com.poly.ductr.app.model.Order;
import com.poly.ductr.app.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> findAll();

    Page<OrderItem> findAll(Pageable pageable);

    <S extends OrderItem> S save(S entity);

    Optional<OrderItem> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    <S extends OrderItem> List<S> saveAll(Iterable<S> entities);

    List<OrderItem> findByOrder(Order order);
}
