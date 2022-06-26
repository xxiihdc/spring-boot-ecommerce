package com.poly.ductr.app.controller;

import com.poly.ductr.app.common.Constants;
import com.poly.ductr.app.common.ResponseObject;
import com.poly.ductr.app.common.SecurityCustom;
import com.poly.ductr.app.config.jwt.JwtProvider;
import com.poly.ductr.app.dto.AddressDto;
import com.poly.ductr.app.model.*;
import com.poly.ductr.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductService productService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SecurityCustom securityCustom;

    @PostMapping
    public ResponseEntity<ResponseObject> order(@RequestBody AddressDto addressDto,
                                                @RequestHeader(value = "Authorization",
                                                        defaultValue = "bar") String jwt) {
        jwt = jwt.replace("Bearer ", "");
        if (!jwtProvider.validateTokenLogin(jwt)) return null;
        String id = jwtProvider.getUsernameFromToken(jwt);
        String address = getAddress(addressDto);
        Order order = new Order();
        order.setDescription(address);
        order.setDate(new Date());
        Customer customer = new Customer(Integer.parseInt(id));
        order.setCustomer(customer);
        order.setStatus(Constants.ORDER_STATUS_WAIT_FOR_CONFIRM);
        order = orderService.save(order);
        List<CartItem> lst = cartItemService.findByCustomer(customer);
        Set<OrderItem> orderItems = new LinkedHashSet<>();
        List<Product> products = new ArrayList<>();
        for (CartItem c : lst) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(new Product(c.getId()));
            orderItem.setQuantity(c.getQuantity());
            orderItem.setUnitPrice(c.getProduct().getUnitPrice());
            Product product = c.getProduct();
            product.setQuantity(product.getQuantity() - c.getQuantity());
            System.out.println(product.getQuantity());
            products.add(product);
            orderItems.add(orderItem);
        }
        System.out.println(products.get(0).getQuantity());
        productService.saveAll(products);
        orderItemService.saveAll(orderItems);
        cartItemService.deleteByCustomer(Integer.parseInt(id));
        order.setOrderItems(orderItems);
        sendMailOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, order, "ok"));
    }

    @GetMapping("customer/all")
    public ResponseEntity<ResponseObject> findOrderById(@RequestHeader(value = "Authorization", defaultValue = "bar") String jwt) {
        jwt = jwt.replace("Bearer ", "");
        String id = jwtProvider.getUsernameFromToken(jwt);
        Customer customer = customerService.findById(Integer.parseInt(id)).get();
        List<Order> lst = orderService.findByCustomer(customer);
        Collections.reverse(lst);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, lst, "Order list of: " + id));
    }

    @GetMapping("/admin/manage/order/{page}")
    public ResponseEntity<ResponseObject> getOrders(@RequestHeader(value = "Authorization", defaultValue = "bar") String jwt,
                                                    @PathVariable Integer page) {
        if (securityCustom.isNotAdmin(jwt)) return securityCustom.unauthorized();
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Order> resultPage = orderService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, resultPage, "OK"));
    }

    @PutMapping("admin/updateOrder/{orderId}/{status}")
    public ResponseEntity<ResponseObject> update(@RequestHeader(value = "Authorization", defaultValue = "bar") String jwt,
                                                 @PathVariable("orderId") Integer orderId,
                                                 @PathVariable("status") Integer status) {
        if (securityCustom.isNotAdmin(jwt)) return securityCustom.unauthorized();
       Order order = orderService.findById(orderId).get();
       order.setStatus(status);
       return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,orderService.save(order),"OK"));
    }

    private void sendMailOrder(Order order) {

    }

    private String getAddress(AddressDto addressDto) {
        System.out.println(addressDto.getApartmentNumber());
        StringBuilder address = new StringBuilder();
        address.append("Phone: " + addressDto.getPhone());
        address.append("\n");
        address.append("Apartment number: " + addressDto.getApartmentNumber());
        address.append("\n");
        address.append("Commune: " + addressDto.getCommune());
        address.append("\n");
        address.append("District: " + addressDto.getDistrict());
        address.append("\n");
        address.append("Province: " + addressDto.getProvince());
        address.append("\n");
        address.append("Message: " + addressDto.getMessage());
        return address.toString();
    }
}
