package com.example.crud.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.Request.RoleRequest;
import com.example.crud.DTO.Request.UpdatePermissionToRoleRequest;
import com.example.crud.DTO.Response.ApiResponse;
import com.example.crud.Services.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleController {
    RoleService roleService;

    @PostMapping("create")
    public ApiResponse<?> createRole(@RequestBody RoleRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.createRole(request));
        return apiResponse;
    }

    @GetMapping("getAll")
    public ApiResponse<?> getAll() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.getAll());
        return apiResponse;
    }

    @PutMapping("update")
    public ApiResponse<?> updatePermissionToRole(@RequestBody UpdatePermissionToRoleRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.updatePermissionToRole(request));
        return apiResponse;
    }

    @DeleteMapping("delete")
    public ApiResponse<Void> deleterole(@RequestParam("role") String role) {
        ApiResponse apiResponse = new ApiResponse<>();
        roleService.deleteRole(role);
        return apiResponse;

    }

}
