package com.poly.ductr.app.controller;

import com.poly.ductr.app.common.ResponseObject;
import com.poly.ductr.app.config.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class JwtRefreshController {
    @Autowired
    JwtProvider jwtProvider;
    @GetMapping("/jwt/update")
    public ResponseEntity<ResponseObject> updateJwt(@RequestHeader(value = "Authorization", defaultValue = "") String jwt){
        jwt = jwt.replace("Bearer ","");
        if(jwtProvider.validateTokenLogin(jwt)){
            String userId = jwtProvider.getUsernameFromToken(jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,jwtProvider.generateTokenLogin(userId),"OK")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(400,"","JWT invalid!!"));
    }
}
