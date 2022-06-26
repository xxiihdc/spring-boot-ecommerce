package com.poly.ductr.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGoogleDto {
    private String id;
    private String email;
    private String name;
    private String giveName;
    private String familyName;
    private String picture;
    private boolean verifiedEmail;
}
