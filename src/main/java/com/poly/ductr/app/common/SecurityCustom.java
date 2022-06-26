package com.poly.ductr.app.common;

import com.poly.ductr.app.config.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SecurityCustom {

    @Autowired
    JwtProvider jwtProvider;
    public boolean isNotAdmin(String token){
        token = token.replace("Bearer ","");
        String username = jwtProvider.getUsernameFromToken(token);
        if(username.equals("2")) return false;
        return  true;
    }
    public ResponseEntity<ResponseObject> unauthorized(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject(403,"","Unauthorized"));
    }
}
