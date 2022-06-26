package com.poly.ductr.app.serviceimpl;


import com.poly.ductr.app.model.CartItem;
import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.repository.CartItemRepository;
import com.poly.ductr.app.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository repository;
    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Transactional
    @Override
    public <S extends CartItem> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }
    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends CartItem> S save(S entity) {
        System.out.println("CartItemService say: Save");
        return repository.save(entity);
    }

    @Override
    public Optional<CartItem> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    public void delete(CartItem entity) {

    }

    @Override
    public void deleteAll() {

    }
    @Override
    public boolean isCartExisted(int cartId) {
        return false;
    }

    @Override
    public List<CartItem> findByCustomer(Customer id) {
        return repository.findByCustomer(id);
    }

    @Override
    public CartItem findByProductIdAndCartId(Integer productId, Integer cartId) {
        return null;
    }

    @Override
    public void deleteByCartId(Integer customerId) {
        repository.deleteByCustomer(customerId);
    }

    @Override
    public void deleteByCustomer(Integer id) {
        repository.deleteByCustomer(id);
    }
}
