package com.poly.ductr.app.controller;


import com.poly.ductr.app.common.ResponseObject;
import com.poly.ductr.app.common.Sha256Utils;
import com.poly.ductr.app.config.jwt.JwtProvider;
import com.poly.ductr.app.domain.MailModel;
import com.poly.ductr.app.dto.LoginDto;

import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.service.CustomerService;
import com.poly.ductr.app.serviceimpl.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    JwtProvider provider;
    @Autowired
    CustomerService customerService;
    @Autowired
    MailerService mailerService;

    @PostMapping("login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDto login) {
        Customer customer = customerService.findByEmail(login.getUsername());
        String token = "";
        String username = "";
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(403,"","Username Not found")
            );
        } else {
            String passwordInput = Sha256Utils.to256(login.getPassword());
            if(!customer.getPassword().equals(passwordInput)) return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(403,"","Wrong password")
            );
        }
        username = customer.getId()+"";
        token = provider.generateTokenLogin(username);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,token,"OK")
        );
    }
    @PostMapping("register")
    public ResponseEntity<ResponseObject> register(@RequestBody LoginDto login) {
        Customer customer = customerService.findByEmail(login.getUsername());
        String token = "";
        String username = "";
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(403,"","Username Not found")
            );
        } else {
            String passwordInput = Sha256Utils.to256(login.getPassword());
            if(!customer.getPassword().equals(passwordInput)) return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(403,"","Wrong password")
            );
        }
        username = customer.getId()+"";
        token = provider.generateTokenLogin(username);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,token,"OK")
        );
    }

    @GetMapping("forget")
    public ResponseEntity<ResponseObject> forget(@RequestParam String email){
        Customer customer = customerService.findByEmail(email);
        if (customer == null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(404,"","Email not found"));
        }
        MailModel mailModel = new MailModel();
        mailModel.setSubject("Z-shop Reset Password");
        mailModel.setTo(customer.getEmail());
        String code = UUID.randomUUID().toString().replaceAll("-","");
        String hashCode = Sha256Utils.to256(code);
        customer.setResetPassword(hashCode);
        customer.setResetPasswordTime(new Date());
        customerService.save(customer);
        String body = "Hello: "+ customer.getCustomerName()+"<br/>";
        body+="Go to localhost:4200/reset/"+code+ " to reset your password. This email is valid for 2 hours";
        mailModel.setBody(body);
        mailerService.push(mailModel);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,"","Check your email"));
    }

    @GetMapping("check/{token}")
    public ResponseEntity<ResponseObject> checkToken(@PathVariable String token){
        String hash = Sha256Utils.to256(token);
        List<Customer> customers = customerService.findByResetPassword(hash);
        if(customers.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(404,"","Token invalid"));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,"","OK"));
    }
}