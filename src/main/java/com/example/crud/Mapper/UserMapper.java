package com.example.crud.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.crud.DTO.Request.UserDTO;
import com.example.crud.DTO.Request.UserUpdateRequest;
import com.example.crud.DTO.Response.UserDTOResponse;
import com.example.crud.Entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserDTO request);

    @Mapping(target = "roles", ignore = true)
    User updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserDTOResponse toUserResponse(User user);

}
