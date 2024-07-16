package com.example.crud.Services;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crud.DTO.Request.RoleRequest;
import com.example.crud.DTO.Request.UpdatePermissionToRoleRequest;
import com.example.crud.DTO.Response.RoleResponse;
import com.example.crud.Exception.AppException;
import com.example.crud.Exception.ErrorCode;
import com.example.crud.Mapper.RoleMapper;
import com.example.crud.Repository.PermissionRepository;
import com.example.crud.Repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }

    public RoleResponse updatePermissionToRole(UpdatePermissionToRoleRequest request) {
        var role = roleRepository.findById(request.getRole())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));

    }

}
