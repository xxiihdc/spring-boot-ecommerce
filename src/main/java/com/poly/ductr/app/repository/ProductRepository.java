package com.poly.ductr.app.repository;


import com.poly.ductr.app.domain.TopSaleProduct;
import com.poly.ductr.app.model.Category;
import com.poly.ductr.app.model.Product;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByStatus(Integer status, Pageable pageable);

    Page<Product> findByStatusAndCategory(Integer status, Category category, Pageable pageable);

    @Query(value = "SELECT products.id,product_name,products.unit_price,products.discount, description FROM products WHERE MATCH(description,product_name) AGAINST( ?1 WITH QUERY expansion)", nativeQuery = true)
    List<Object[]> findByKeyWordFullText(String key);

    @Modifying
    @Query(value = "select products.id as productId,products.product_name as productName,products.unit_price" +
            " as unitPrice,products.discount as discount, sum(order_items.quantity) as Quantity from order_items " +
            "INNER JOIN products ON products.id = order_items.product_id\n" +
            "INNER JOIN categories on products.category_id = categories.category_id " +
            "WHERE categories.category_id = ?1 " +
            "group by product_id " +
            "ORDER BY sum(order_items.quantity) DESC  LIMIT 9", nativeQuery = true)
    List<Object[]> findTopSaleByCategory(Integer categoryId);
}
