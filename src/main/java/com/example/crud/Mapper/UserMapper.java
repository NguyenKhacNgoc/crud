package com.example.crud.Mapper;

import org.mapstruct.Mapper;

import com.example.crud.DTO.Request.UserDTO;
import com.example.crud.Entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO request);

}
