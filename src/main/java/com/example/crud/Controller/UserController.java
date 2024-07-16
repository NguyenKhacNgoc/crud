package com.example.crud.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.Request.ChangePassWordRequest;
import com.example.crud.DTO.Request.UpdateRoleToUserRequest;
import com.example.crud.DTO.Request.UserDTO;
import com.example.crud.DTO.Request.UserUpdateRequest;
import com.example.crud.DTO.Request.idRequest;
import com.example.crud.DTO.Response.ApiResponse;

import com.example.crud.DTO.Response.UserDTOResponse;
import com.example.crud.Entity.User;
import com.example.crud.Exception.AppException;
import com.example.crud.Exception.ErrorCode;
import com.example.crud.Repository.UserRepository;
import com.example.crud.Services.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/user")
@SuppressWarnings({ "unchecked", "rawtypes" })

public class UserController {
    UserService userService;
    UserRepository userRepository;

    @GetMapping("getUsers")
    public ApiResponse<?> getUser(@RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAllUser(pageNumber, pageSize));
        return apiResponse;
    }

    @GetMapping("findUser")
    public User fiUser(@RequestParam("userName") String userName) {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @PostMapping("create")
    public ApiResponse<UserDTOResponse> createUser(@Valid @RequestBody UserDTO request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;

    }

    @PutMapping("update")
    public ApiResponse<?> updateUser(@RequestBody UserUpdateRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(request));
        return apiResponse;
    }

    @PutMapping("updateRoleToUser")
    public ApiResponse<?> updateRoleForUser(@RequestBody UpdateRoleToUserRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateRoleToUser(request));
        return apiResponse;
    }

    @DeleteMapping("delete")
    public ApiResponse<?> deleteUser(@RequestBody idRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.deleteUser(request.getId()));
        return apiResponse;
    }

    @PutMapping("changePassWord")
    public ApiResponse<UserDTOResponse> changePassword(@RequestBody ChangePassWordRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.changePassWord(request));
        return apiResponse;
    }

    @GetMapping("getUser")
    public ApiResponse<UserDTOResponse> getUser(@RequestParam("id") String id) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser(id));
        return apiResponse;
    }

    @GetMapping("getMyInfo")
    public ApiResponse<UserDTOResponse> getMyInfo() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }

}
