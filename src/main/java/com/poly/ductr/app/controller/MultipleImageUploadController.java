package com.poly.ductr.app.controller;

import com.poly.ductr.app.common.SecurityCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/admin/uploads/sub")
public class MultipleImageUploadController {

    @Autowired
    SecurityCustom securityCustom;

    @PostMapping()
    public ResponseEntity<Void> uploadPolicyDocument(@RequestParam("document") List<MultipartFile> multipartFile,
                                                     @RequestParam String id,
                                                     @RequestHeader(value = "Authorization", defaultValue = "bar") String jwt) {
        if (securityCustom.isNotAdmin(jwt)) return null;
        Path path= Paths.get("uploads");
        System.out.println("upload sub img");
        try {
            for(int i = 0; i < multipartFile.size(); i++){
                InputStream inputStream = multipartFile.get(i).getInputStream();
                String fileName = "product_"+id+"_sub_img"+(i+1)+".jpg";
                Files.copy(inputStream,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
