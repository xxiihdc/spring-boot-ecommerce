package com.poly.ductr.app.service;



import com.poly.ductr.app.model.CartItem;
import com.poly.ductr.app.model.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItem> findAll();

    <S extends CartItem> S saveAndFlush(S entity);

    void flush();

    <S extends CartItem> S save(S entity);

    Optional<CartItem> findById(Integer integer);

    boolean existsById(Integer integer);

    void deleteById(Integer integer);

    void delete(CartItem entity);

    void deleteAll();



    //================================ My Function ==============================
    boolean isCartExisted(int cartId);

    List<CartItem> findByCustomer(Customer customer);



    CartItem findByProductIdAndCartId(Integer productId, Integer cartId);

    void deleteByCartId(Integer cartId);

    void deleteByCustomer(Integer id);
}
