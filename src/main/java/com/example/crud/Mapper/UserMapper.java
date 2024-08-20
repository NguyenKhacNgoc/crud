package com.example.crud.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.crud.dto.request.UserCreationRequest;
import com.example.crud.dto.request.UserUpdateRequest;
import com.example.crud.dto.response.UserResponse;
import com.example.crud.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserCreationRequest request);

    User updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User user);

}
