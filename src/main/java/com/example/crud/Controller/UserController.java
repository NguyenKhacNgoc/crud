package com.example.crud.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.dto.request.UserCreationRequest;
import com.example.crud.dto.request.UserUpdateRequest;
import com.example.crud.dto.response.ApiResponse;
import com.example.crud.dto.response.PageResponse;
import com.example.crud.dto.response.UserResponse;
import com.example.crud.entity.User;
import com.example.crud.exception.AppException;
import com.example.crud.exception.ErrorCode;
import com.example.crud.repository.UserRepository;
import com.example.crud.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api")
@SuppressWarnings({ "unchecked", "rawtypes" })

public class UserController {
    UserService userService;
    UserRepository userRepository;

    @PostMapping("register")
    public void createUser(@RequestBody UserCreationRequest request) {
        userService.createUser(request);
    }

    @GetMapping("users")
    public ApiResponse<PageResponse<UserResponse>> getUser(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAllUser(page, size));
        return apiResponse;
    }

    @GetMapping("findUser")
    public User fiUser(@RequestParam("userName") String userName) {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @DeleteMapping("user/delete/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("getUser")
    public ApiResponse<UserResponse> getUser(@RequestParam("id") String id) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser(id));
        return apiResponse;
    }

    @GetMapping("getMyInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }

    @PutMapping("/user/update/{id}")
    public ApiResponse<?> updateUser(@PathVariable String id,
            @RequestBody UserUpdateRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(id, request));
        return apiResponse;

    }

    @PutMapping("/user/update")
    public ApiResponse<?> updateMyUser(@RequestBody UserUpdateRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(null, request));
        return apiResponse;

    }

}
