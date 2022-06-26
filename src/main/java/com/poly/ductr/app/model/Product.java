package com.poly.ductr.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.ductr.app.common.DateUtils;
import com.poly.ductr.app.dto.ProductDto;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_name")
    private String name;
    @Column (name = "quantity")
    private Integer quantity;
    @Column (name = "unit_price")
    private Double unitPrice;
    @Column (name = "description")
    private String description;
    @Column (name = "discount")
    private Double discount;
    @Column (name = "status")
    private Integer status;
    @Column (name = "entered_date")
    private Date enteredDate;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade=CascadeType.ALL)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public Product(Integer id) {
        this.id = id;
    }

    public ProductDto toProductDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setCategoryId(p.getCategory().getId());
        dto.setEnteredDate(DateUtils.toString(p.getEnteredDate(),"MM/dd/yyyy"));
        dto.setName(p.getName());
        dto.setQuantity(p.getQuantity());
        dto.setDiscount(p.getDiscount());
        dto.setDescription(p.getDescription());
        dto.setUnitPrice(p.getUnitPrice());
        dto.setId(p.getId());
        dto.setStatus(p.getStatus());
        return dto;
    }
}
