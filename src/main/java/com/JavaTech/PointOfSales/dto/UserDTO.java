package com.JavaTech.PointOfSales.dto;

import com.JavaTech.PointOfSales.model.Branch;
import lombok.*;


@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    private String avatar;
    private String fullName;
    private String phone;
    private String email;
    private String gender;
    private String username;
    private boolean activated;
    private boolean unlocked;
    private boolean firstLogin;
    private String verificationCode;
    private Branch branch;
}