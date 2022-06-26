package com.poly.ductr.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopSaleProduct {
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Double discount;
    private Integer quantity;
}
