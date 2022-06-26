package com.poly.ductr.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.ductr.app.dto.CartItemDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product;

    @Column (name = "quantity")
    private Integer quantity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;
}
