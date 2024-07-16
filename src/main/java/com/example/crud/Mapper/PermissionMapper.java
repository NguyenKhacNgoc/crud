package com.example.crud.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.crud.DTO.Request.PermissionRequest;
import com.example.crud.DTO.Response.PermissionResponse;
import com.example.crud.Entity.Permission;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
