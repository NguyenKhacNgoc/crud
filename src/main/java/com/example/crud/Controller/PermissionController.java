package com.example.crud.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.Request.PermissionRequest;
import com.example.crud.DTO.Response.ApiResponse;
import com.example.crud.Services.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("create")
    public ApiResponse<?> createPermission(@RequestBody PermissionRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.createPermission(request));
        return apiResponse;
    }

    @GetMapping("getAll")
    public ApiResponse<?> getAll() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.getAll());
        return apiResponse;
    }

    @DeleteMapping("delete")
    public ApiResponse<Void> deletePermission(@RequestParam("permission") String permission) {
        ApiResponse apiResponse = new ApiResponse<>();
        permissionService.deletePermission(permission);
        return apiResponse;

    }

}
