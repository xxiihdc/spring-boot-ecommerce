package com.poly.ductr.app.common;


import java.security.NoSuchAlgorithmException;

public class Sha256Utils {
    public Sha256Utils()  {
    }
    public static String to256(String text){
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
        return  sha256hex;
    }
}
