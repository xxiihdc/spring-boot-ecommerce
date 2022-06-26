package com.poly.ductr.app.dto;

import com.poly.ductr.app.common.DateUtils;
import com.poly.ductr.app.model.Category;
import com.poly.ductr.app.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private Integer quantity;
    private Double unitPrice;
    private String description;
    private Double discount;
    private Integer status;
    private String enteredDate;
    private Integer categoryId;

    public Product toProductFromDto(ProductDto dto) {
        Product p = new Product();
        p.setEnteredDate(DateUtils.toDate(dto.getEnteredDate(),"yyyy-MM-dd"));
        Category category = new Category();
        category.setId(dto.getCategoryId());
        p.setCategory(category);
        p.setId(dto.getId());
        p.setDescription(dto.getDescription());
        p.setDiscount(dto.getDiscount());
        p.setName(dto.getName());
        p.setQuantity(dto.getQuantity());
        p.setStatus(dto.getStatus());
        p.setUnitPrice(dto.getUnitPrice());
        System.out.println(p.getQuantity() + p.getDiscount() + p.getStatus());
        return p;
    }
}
