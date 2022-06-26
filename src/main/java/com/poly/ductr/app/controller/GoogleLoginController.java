package com.poly.ductr.app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.poly.ductr.app.common.Constants;
import com.poly.ductr.app.config.jwt.JwtProvider;
import com.poly.ductr.app.dto.UserGoogleDto;
import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.service.CustomerService;

import com.poly.ductr.app.serviceimpl.MailerService;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Controller
@CrossOrigin("*")
public class GoogleLoginController {
    @Autowired
    CustomerService customerService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MailerService mailerService;

    @GetMapping("/login/oauth2/code/google")
    public ModelAndView loginWithGoogle(@RequestParam String code){
        String accessToken = null;
        try {
            accessToken = getToken(code);
            UserGoogleDto user = getUserInfo(accessToken);
            Customer customer = customerService.findByEmail(user.getEmail());
            if (customer == null ){
                customer = new Customer();
                customer.setCustomerName(user.getName());
                customer.setAdmin(false);
                customer.setEmail(user.getEmail());
                customer.setRegisterDate(new Date());
                customer.setStatus(true);
                customer = customerService.save(customer);
                sendEmail();
            }
            String token = jwtProvider.generateTokenLogin(customer.getId()+"");
            String url = "http://localhost:4200/google/"+token;
            return new ModelAndView("redirect:" + url);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @GetMapping("test")
    public ModelAndView test(){
        return new ModelAndView("redirect:" + "https://google.com");
    }
    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }
    public static UserGoogleDto getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        Content cont = Request.Get(link).execute().returnContent();
        String response;
        response=cont.asString(StandardCharsets.UTF_8);
        UserGoogleDto googlePojo = new Gson().fromJson(response, UserGoogleDto.class);
        return googlePojo;
    }

    void sendEmail() {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("ductvhpd04166@fpt.edu.vn");
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//
            mailerService.push("tran.van.hoai.duc@sun-asterisk.com","Welcome to Z-shop",
                    "<h1>Happy shopping</h1> \n <h1></h1>");
    }
}
