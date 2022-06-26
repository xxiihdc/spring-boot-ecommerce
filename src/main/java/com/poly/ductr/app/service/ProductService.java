package com.poly.ductr.app.service;

import com.poly.ductr.app.domain.TopSaleProduct;
import com.poly.ductr.app.model.Category;
import com.poly.ductr.app.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    List<Product> findAll(Sort sort);

    <S extends Product> S save(S entity);

    Optional<Product> findById(Integer integer);

    Page<Product> findByStatus(Integer status, Pageable pageable);

    boolean existsById(Integer integer);

    Page<Product> findAll(Pageable pageable);

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    List<TopSaleProduct> findTopSaleByCategory(Integer categoryId);

    List<Product> findByKeyWordFullText(String key);

    Page<Product> findByStatusAndCategory(Integer status, Category category, Pageable pageable);
}
