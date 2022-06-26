package com.poly.ductr.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String phone;
    private String apartmentNumber;
    private String district;
    private String commune;
    private String province;
    private String message;
}
