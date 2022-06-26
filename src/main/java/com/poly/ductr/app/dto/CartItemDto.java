package com.poly.ductr.app.dto;

import com.poly.ductr.app.model.CartItem;
import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.model.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private Integer customerId;

    public CartItem toCartItem() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(quantity);
        cartItem1.setId(id);
        cartItem1.setProduct(new Product(productId));
        cartItem1.setCustomer(new Customer(customerId));
        return cartItem1;
    }
}
