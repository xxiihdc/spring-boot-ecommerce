package com.poly.ductr.app.controller;

import com.poly.ductr.app.common.*;

import com.poly.ductr.app.dto.ProductDto;
import com.poly.ductr.app.model.Category;
import com.poly.ductr.app.model.Product;
import com.poly.ductr.app.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    ProductService service;

    @Autowired
    SecurityCustom securityCustom;

    @GetMapping("new")
    public ResponseEntity<ResponseObject> findByDate(){
        Sort sort = Sort.by(Sort.Direction.DESC,"enteredDate");
        Pageable pageable = PageRequest.of(0,10,sort);
        List<Product> lst = service.findAll(pageable).getContent();
        List<ProductDto> lstDto = new ArrayList<>();
        try {
            lstDto = DtoUtils.productDtoList(lst);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,lstDto,"Find 10 Product order by date")
        );
    }
    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> findProductById(@PathVariable Integer id){
        Optional<Product> p = service.findById(id);
        Product p2;
        ProductDto dto;
        if (p.isPresent()) {
            p2 = p.get();
            dto = p2.toProductDto(p2);
        }
        else dto = null;
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,dto,"")
        );
    }

    @PostMapping("admin/save")
    public ResponseEntity<ResponseObject> save(@RequestBody ProductDto product,
                                               @RequestHeader(value = "Authorization", defaultValue = "bar") String jwt){
        if (securityCustom.isNotAdmin(jwt)) return securityCustom.unauthorized();
        if (product.getEnteredDate() == null){
            Date dates = new Date();
            String date = DateUtils.toString(dates,"yyyy-MM-dd");
            product.setEnteredDate(date);
        }
        Product newProduct = service.save(product.toProductFromDto(product));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,newProduct,"OK")
        );
    }
    @PostMapping("admin/upload")
    public ResponseEntity<ResponseObject> upload(@RequestParam MultipartFile file, @RequestParam String id,
            @RequestHeader(value = "Authorization", defaultValue = "bar") String jwt){
        System.out.println(securityCustom.isNotAdmin(jwt));
        if (securityCustom.isNotAdmin(jwt)){
            return securityCustom.unauthorized();
        }
        Path path= Paths.get("uploads");
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = "avatar_"+id+".jpg";
            Files.copy(inputStream,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"","OK")
        );
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<ResponseObject> getProductPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,5, Sort.by("enteredDate").descending());
        Page<Product> resultPage = service.findByStatus(Constants.PRODUCT_STATUS_AVAILABLE,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,resultPage,"OK"));
    }
    @GetMapping("stop/{page}")
    public ResponseEntity<ResponseObject> getProductStop(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,5, Sort.by("enteredDate").descending());
        Page<Product> resultPage = service.findByStatus(Constants.PRODUCT_STATUS_STOP_BUSINESS,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,resultPage,"OK"));
    }
    @GetMapping("similar/category/{id}")
    public ResponseEntity<ResponseObject> getSimilarProductByCategory(@PathVariable Integer id){
        Product product = service.findById(id).get();
        int cid = product.getCategory().getId();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,
               service.findTopSaleByCategory(cid),""));
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseObject> search(@RequestParam(defaultValue = "") String query,
                                                 @RequestParam(defaultValue = "0") Integer page){
        List<Product> lst = service.findByKeyWordFullText(query);
        PagedListHolder resultPage = new PagedListHolder(lst);
        resultPage.setPageSize(12);
        resultPage.setPage(page);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, resultPage,"OK"));
    }
    @GetMapping("category/{page}")
    public ResponseEntity<ResponseObject> search1( @PathVariable Integer page, @RequestParam String type){
        Pageable pageable = PageRequest.of(page,9, Sort.by("enteredDate").descending());
        if(type.equals("all")){
            Page<Product> resultPage = service.findByStatus(Constants.PRODUCT_STATUS_AVAILABLE,pageable);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, resultPage,"OK"));
        }
        int c = Integer.parseInt(type);
        Category category = new Category(c);
        Page<Product> resultPage = service.findByStatusAndCategory(Constants.PRODUCT_STATUS_AVAILABLE,category,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, resultPage,"OK"));
    }
    @GetMapping("top-sale")
    public ResponseEntity<ResponseObject> getTopSaleProduct(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("discount").descending());
        Page<Product> resultPage = service.findByStatus(Constants.PRODUCT_STATUS_AVAILABLE,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,resultPage.getContent(),"OK"));
    }

    @GetMapping("admin/test")
    public ResponseEntity<ResponseObject> test(@RequestHeader(value = "Authorization", defaultValue = "bar") String jwt){
        if (securityCustom.isNotAdmin(jwt)) return securityCustom.unauthorized();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject());
    }
    @GetMapping
    public ResponseEntity<ResponseObject> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, service.findAll(),"Find all"));
    }

}
