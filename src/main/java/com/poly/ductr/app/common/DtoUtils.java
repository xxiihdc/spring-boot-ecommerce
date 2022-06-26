package com.poly.ductr.app.common;

import com.poly.ductr.app.dto.ProductDto;
import com.poly.ductr.app.model.Product;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DtoUtils {
    public static List<ProductDto> productDtoList(List<Product> lst) throws InvocationTargetException, IllegalAccessException {
        List<ProductDto> lstDto = new ArrayList<>();
        for(Product p:lst){
            lstDto.add(p.toProductDto(p));
        }
        return lstDto;
    }
}
