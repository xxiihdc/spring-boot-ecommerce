package com.poly.ductr.app.serviceimpl;



import com.poly.ductr.app.domain.TopSaleProduct;
import com.poly.ductr.app.model.Category;
import com.poly.ductr.app.model.Product;
import com.poly.ductr.app.repository.ProductRepository;
import com.poly.ductr.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public <S extends Product> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Product> findById(Integer integer) {
        return repository.findById(integer);
    }

    @Override
    public Page<Product> findByStatus(Integer status, Pageable pageable){
        return this.repository.findByStatus(status,pageable);
    }
    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public List<TopSaleProduct> findTopSaleByCategory(Integer categoryId) {
        List<Object[]> lst = repository.findTopSaleByCategory(categoryId);
        List<TopSaleProduct> topSaleProducts = new ArrayList<>();
        for(Object[] objs : lst){
            TopSaleProduct topSaleProduct = new
                    TopSaleProduct(Integer.parseInt(objs[0]+""),objs[1]+"",Double.parseDouble(objs[2]+""),Double.parseDouble(objs[3]+"")
                    ,Integer.parseInt(objs[4]+""));
            topSaleProducts.add(topSaleProduct);
        }
        return topSaleProducts;
    }
    @Override
    public List<Product> findByKeyWordFullText(String key) {
        List<Object[]> lst= repository.findByKeyWordFullText(key);
        List<Product> lstP = new ArrayList<>();
        for(Object[] objs: lst){
            Product p = new Product();
            p.setId(Integer.parseInt(objs[0]+""));
            p.setName(objs[1]+"");
            p.setUnitPrice(Double.parseDouble(objs[2]+""));
            p.setDiscount(Double.parseDouble(objs[3]+""));
            p.setDescription(objs[4]+"");
               lstP.add(p);
        }
        return lstP;
    }

    @Override
    public Page<Product> findByStatusAndCategory(Integer status, Category category, Pageable pageable) {
        return repository.findByStatusAndCategory(status, category, pageable);
    }
}
