package com.poly.ductr.app.serviceimpl;

import com.poly.ductr.app.model.Order;
import com.poly.ductr.app.model.OrderItem;
import com.poly.ductr.app.repository.OrderItemRepository;
import com.poly.ductr.app.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemRepository repository;
    @Override
    public List<OrderItem> findAll() {
        return null;
    }

    @Override
    public Page<OrderItem> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends OrderItem> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<OrderItem> findById(Integer integer) {
        return Optional.empty();
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
    public <S extends OrderItem> List<S> saveAll(Iterable<S> entities) {

        return repository.saveAll(entities);
    }

    @Override
    public List<OrderItem> findByOrder(Order order) {
        return this.repository.findByOrder(order);
    }
}
