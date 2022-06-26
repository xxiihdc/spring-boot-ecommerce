package com.poly.ductr.app.other;

import com.poly.ductr.app.common.Constants;
import com.poly.ductr.app.common.DateUtils;
import com.poly.ductr.app.model.*;
import com.poly.ductr.app.service.CustomerService;
import com.poly.ductr.app.service.OrderItemService;
import com.poly.ductr.app.service.OrderService;
import com.poly.ductr.app.service.ProductService;
import com.poly.ductr.app.serviceimpl.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
public class InitFakeData {
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    MailerService mailerService;

 //   @GetMapping("admin/init/product")
    public String initProduct() {
        File myObj = new File("dummy-data/product.txt");
        Random random = new Random();
        try {
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Product product = new Product();
                product.setStatus(2);
                product.setQuantity(1000);
                product.setEnteredDate(DateUtils.toDate("2022-01-01", "yyyy-MM-dd"));
                product.setName(data);
                product.setCategory(new Category((i%12)+1));
                product.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. " +
                        "Repellendus dicta voluptatem delectus tempora sit ab. " +
                        "Quis debitis repudiandae, molestiae quisquam iusto et, " +
                        "quas doloribus porro obcaecati tempore, consectetur sapiente aspernatur.");
                product.setDiscount(random.nextDouble(10));
                product.setUnitPrice(random.nextDouble(50,100));
                productService.save(product);
                i++;
                System.out.println("loop: " + i);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "OK";
    }
//    @GetMapping("admin/init/dummyproduct")
    public String initDummyProduct(){
        Random random = new Random();
        for(int j = 0; j <100; j++){
            Product product = new Product();
            product.setStatus(random.nextInt(1,2));
            product.setQuantity(1000);
            product.setEnteredDate(DateUtils.toDate("2022-01-01", "yyyy-MM-dd"));
            product.setName("Dummy Product name: " + j);
            product.setCategory(new Category((j%12)+1));
            product.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. " +
                    "Repellendus dicta voluptatem delectus tempora sit ab. " +
                    "Quis debitis repudiandae, molestiae quisquam iusto et, " +
                    "quas doloribus porro obcaecati tempore, consectetur sapiente aspernatur.");
            product.setDiscount(random.nextDouble(10));
            product.setUnitPrice(random.nextDouble(50,100));
            productService.save(product);
            System.out.println(j);
        }
        return "OK";
    }

 //   @GetMapping("/admin/init/customer")
    public String initCustomer() {
        File myObj = new File("dummy-data/name.txt");
        Random random = new Random();
        try {
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                i++;
                String data = myReader.nextLine();
                Customer customer = new Customer();
                customer.setCustomerName(data.trim());
                customer.setStatus(true);
                customer.setAdmin(false);
                customer.setEmail("dummy-email-" + i + "@gmail.com");
                String phone = String.format("%09d", random.nextInt(1000000000));
                customer.setPhone("0" + phone);
                customer.setPassword("697a29ab74d07b045e88d3964218c398724bdd22af526f89572f941b826822ed");
                customerService.save(customer);
                System.out.println("loop: " + i);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "OK";
    }
//    @GetMapping("/admin/init/order")
    public String dummyOrder(){
        File myObj = new File("dummy-data/date.txt");
        Random random = new Random();
        try {
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Order order = new Order();
                order.setDate(DateUtils.toDate(data,"MM/dd/yyyy"));
                order.setCustomer(new Customer(random.nextInt(105,203)));
                order.setStatus(Constants.ORDER_STATUS_DONE);
                Set<OrderItem> orderItemSet = new HashSet<>();
                order = orderService.save(order);
                for(int j =0; j<5;j++){
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(random.nextInt(1,10));
                    orderItem.setProduct(new Product(random.nextInt(1,51)));
                    orderItem.setUnitPrice(100d);
                    orderItemSet.add(orderItem);
                    orderItem.setOrder(order);
                }
                orderItemService.saveAll(orderItemSet);
                System.out.println(i);
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "OK";
    }
 //   @GetMapping("admin/init/test")
    public String test(){
        Random random = new Random();
        Order order = new Order();
        order.setStatus(Constants.ORDER_STATUS_DONE);
        order.setDate(new Date());
        Set<OrderItem> orderItemSet = new HashSet<>();
        for(int i =0; i<5;i++){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(random.nextInt(1,10));
            orderItem.setProduct(new Product(i+1));
            orderItem.setUnitPrice(100d);
            orderItemSet.add(orderItem);
        }
        order.setOrderItems(orderItemSet);
        orderService.save(order);
        return "OK";
    }
    @PostMapping("admin/init/image")
    public String uploadImage(@RequestParam MultipartFile file){
        Path path= Paths.get("uploads");
        try {

            for (int i = 11; i < 139; i++){
               // String fileName = "avatar_"+i+".jpg";
                //Files.copy(inputStream,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                for(int j =1;j<=3;j++){
                    InputStream inputStream = file.getInputStream();
                    String filename = "product_"+i+"_sub_img"+j+".jpg";
                    Files.copy(inputStream,path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                }
                System.out.println("loop: "+ i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "OK";
    }
//    @GetMapping("/send")
    public String send(){
        mailerService.push("ductvhpd04166@fpt.edu.vn","Testing from Spring Boot",
                "<h1>Hello World</h1> \n <h1>Spring Boot Email</h1>");
        return "OK";
    }
    @GetMapping("test/admin/test1")
    public String test1(){
        return "Test";
    }
}
