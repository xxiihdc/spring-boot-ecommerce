package com.poly.ductr.app.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/image")
public class ImageUploadController {
    @GetMapping("{id}")
    public ResponseEntity<ByteArrayResource> getImg(@PathVariable String id) {
        String file = "avatar_"+id+".jpg";
        return getByteArrayResourceResponseEntity(file);
    }
    @GetMapping("/sub/{pid}/{sid}")
    public ResponseEntity<ByteArrayResource> getSubImg(@PathVariable("pid") String pid, @PathVariable("sid") String sid) {
        String file = "product_"+pid+"_sub_img"+sid+".jpg";
        return getByteArrayResourceResponseEntity(file);
    }

    private ResponseEntity<ByteArrayResource> getByteArrayResourceResponseEntity(String file) {
        Path fileName = Paths.get("uploads", file);
        try {
            byte[] buffer = Files.readAllBytes(fileName);
            ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
            return ResponseEntity.status(HttpStatus.OK).contentLength(buffer.length)
                    .contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
