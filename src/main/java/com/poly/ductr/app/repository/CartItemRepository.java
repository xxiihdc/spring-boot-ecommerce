package com.poly.ductr.app.repository;

import com.poly.ductr.app.model.CartItem;
import com.poly.ductr.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    List<CartItem> findByCustomer(Customer customer);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_items where customer_id = ?1", nativeQuery = true)
    void deleteByCustomer(Integer id);

}
