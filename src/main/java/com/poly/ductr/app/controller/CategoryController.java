package com.poly.ductr.app.controller;

import com.poly.ductr.app.common.ResponseObject;
import com.poly.ductr.app.common.SecurityCustom;
import com.poly.ductr.app.model.Category;
import com.poly.ductr.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService service;
    @Autowired
    SecurityCustom securityCustom;
    @PostMapping("/save")
    public ResponseEntity<ResponseObject> save(@RequestBody Category category,
                                               @RequestHeader(value = "Authorization", defaultValue = "bar") String jwt){
        if (securityCustom.isNotAdmin(jwt)) return securityCustom.unauthorized();
        Category newCategory = service.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,newCategory,"OK")
        );
    }
    @GetMapping
    public ResponseEntity<ResponseObject> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,service.findAll(),"OK")
        );
    }
}