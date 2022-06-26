package com.poly.ductr.app.config.jwt;


import com.poly.ductr.app.common.Constants;
import com.poly.ductr.app.config.userpricle.UserPrinciple;
import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {
    @Autowired
    CustomerService customerService;
    public String generateTokenLogin(String username) {//username = column id in table accounts
        String token = null;
        Customer customer = customerService.findById(Integer.parseInt(username)).get();
        UserPrinciple userPrinciple = UserPrinciple.build(customer);
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(Constants.USERNAME, username);
            builder.expirationTime(generateExpirationTime());
            String [] roles = getRolesFromUsername(username);
            builder.claim("ROLES_FOR_FE",roles);
            builder.claim("ROLES",userPrinciple.getRoles());
            builder.claim("VIEW_NAME",userPrinciple.getViewName());
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    private String[] getRolesFromUsername(String username) {
        Customer customer = customerService.findById(Integer.parseInt(username)).get();
        String [] arr = new String[2];
        if (customer.getAdmin()){
            arr[0] = "ROLE_ADMIN";
            arr[1] = "ROLE_USER";
        }else{
            arr[0] = "ROLE_USER";
        }
        return  arr;
    }

    public JWTClaimsSet getClaimsSetFromToken(String token) {
        JWTClaimsSet claimsSet = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claimsSet = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claimsSet;
    }

    private Date generateExpirationTime() {
        long expireTime = Constants.JWT_EXPIRATION_TIME;
        Date currentDate= new Date();
        Date expDate = new Date(currentDate.getTime()+expireTime);
        return expDate;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        JWTClaimsSet claimsSet = getClaimsSetFromToken(token);
        expiration = claimsSet.getExpirationTime();
        return expiration;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            JWTClaimsSet claimsSet = getClaimsSetFromToken(token);
            username = claimsSet.getStringClaim(Constants.USERNAME);
        } catch (Exception e) {
        }
        return username;
    }

    private byte[] generateShareSecret() {
        byte[] sharedSecret = new byte[32];
        sharedSecret = Constants.JWT_SECRET_KEY.getBytes();
        return sharedSecret;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateTokenLogin(String token) {
        if (token == null || token.trim().length() == 0) return false;
        String username = getUsernameFromToken(token);
        if (username == null || username.isEmpty()) return false;
        if (isTokenExpired(token)) return false;
        return true;
    }
}

