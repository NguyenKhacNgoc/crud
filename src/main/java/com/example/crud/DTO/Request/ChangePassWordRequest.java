package com.example.crud.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePassWordRequest {
    private String userName;
    private String passWord;
    private String newPassWord;
}
