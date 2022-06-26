package com.poly.ductr.app.controller;

import com.poly.ductr.app.common.ResponseObject;
import com.poly.ductr.app.config.jwt.JwtProvider;
import com.poly.ductr.app.dto.CartItemDto;
import com.poly.ductr.app.model.CartItem;
import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.service.CartItemService;
import com.poly.ductr.app.service.CustomerService;
import com.poly.ductr.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getCart(@RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
        if (jwt.equals(""))
            return ResponseEntity.ok().body(new ResponseObject(404, new Object[0], "Not permitted"));
        String username = jwtProvider.getUsernameFromToken(jwt.replace("Bearer ", ""));
        Customer customer = customerService.findById(Integer.parseInt(username)).get();
        List<CartItem> lst = cartItemService.findByCustomer(customer);
        return ResponseEntity.ok().body(new ResponseObject(200, lst, "OK"));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addToCart(
            @RequestHeader(value = "Authorization", defaultValue = "bar") String jwt,
            @RequestBody CartItemDto cartItem) {
        jwt = jwt.replace("Bearer ", "");
        if (!jwtProvider.validateTokenLogin(jwt)) return null;
        String id = jwtProvider.getUsernameFromToken(jwt);
        Customer customer = customerService.findById(Integer.parseInt(id)).get();
        Set<CartItem> cartItemSet = customer.getCartItems();
        for (CartItem it : cartItemSet) {
            if (it.getProduct().getId() == cartItem.getProductId()) {
                it.setQuantity(it.getQuantity() + cartItem.getQuantity());
                cartItemService.save(it);
                List<CartItem> lst = cartItemService.findByCustomer(customer);
                return ResponseEntity.ok().body(new ResponseObject(200, lst, "OK"));
            }
        }
        cartItem.setCustomerId(Integer.parseInt(id));
        CartItem cartItem1 = cartItem.toCartItem();
        cartItemService.saveAndFlush(cartItem1);
        cartItemService.flush();
        List<CartItem> lst = cartItemService.findByCustomer(customer);
        lst = lst.stream().map(item->{
            if (item.getProduct().getName()==null){
                item.setProduct(productService.findById(item.getProduct().getId()).get());
            }
            return item;
        }).toList();
        return ResponseEntity.ok().body(new ResponseObject(200, lst, "OK"));
    }
    @DeleteMapping("/api/v1/cart/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@RequestHeader(value = "Authorization", defaultValue = "bar") String jwt,
                                                   @PathVariable Integer id){
        jwt = jwt.replace("Bearer ", "");
        if (!jwtProvider.validateTokenLogin(jwt)) return null;
        cartItemService.deleteById(id);
        String cid = jwtProvider.getUsernameFromToken(jwt);
        Customer customer = customerService.findById(Integer.parseInt(cid)).get();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject
                (204,cartItemService.findByCustomer(customer),"Delete done"));
    }
}
