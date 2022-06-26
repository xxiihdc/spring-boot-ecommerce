package com.poly.ductr.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Integer id;
    @Column(name = "customer_name", length = 50)
    private String customerName;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "register_date")
    @Temporal(TemporalType.DATE)
    private Date registerDate;

    @JsonIgnore
    @Column(name = "status")
    private Boolean status;

    @JsonIgnore
    @Column(name = "admin")
    private Boolean admin;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "reset_password")
    private String resetPassword;

    @JsonIgnore
    @Column(name = "reset_password_time")
    private Date resetPasswordTime;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public Customer(Integer customerId) {
        this.id = customerId;
    }
}
