package com.selling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String telephone;
    private String role;
    private String nic;
    private String registration_date;
    private String status;
    private String userName;
    private String password;
}
