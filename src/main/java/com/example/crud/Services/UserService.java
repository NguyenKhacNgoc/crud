package com.example.crud.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.Request.ChangePassWordRequest;
import com.example.crud.DTO.Request.UpdateRoleToUserRequest;
import com.example.crud.DTO.Request.UserDTO;
import com.example.crud.DTO.Request.UserUpdateRequest;
import com.example.crud.DTO.Response.UserDTOResponse;
import com.example.crud.Entity.User;
import com.example.crud.Enums.Role;
import com.example.crud.Exception.AppException;
import com.example.crud.Exception.ErrorCode;
import com.example.crud.Mapper.UserMapper;
import com.example.crud.Repository.RoleRepository;
import com.example.crud.Repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    public UserDTOResponse toUserDTOResponse(User user) {
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserDTOResponse> getAllUser(Integer pageNumber, Integer pageSize) {
        Pageable userPage = PageRequest.of(pageNumber, pageSize);
        List<User> users = userRepository.findAll(userPage).getContent();
        List<UserDTOResponse> userDTOResponses = new ArrayList<>();
        for (User user : users) {
            userDTOResponses.add(toUserDTOResponse(user));

        }
        return userDTOResponses;

    }

    @PostAuthorize("returnObject.userName == authentication.name")
    public UserDTOResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

    }

    public UserDTOResponse createUser(UserDTO request) {
        if (userRepository.findUserByUserName(request.getUserName()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);

        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        // user.setRole(roles);
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public UserDTOResponse changePassWord(ChangePassWordRequest request) {
        User user = userRepository.findUserByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassWord(), user.getPassWord());
        if (authenticated) {
            user.setPassWord(passwordEncoder.encode(request.getNewPassWord()));
            userRepository.save(user);
            return userMapper.toUserResponse(user);
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);

    }

    public UserDTOResponse updateUser(UserUpdateRequest request) {
        if (userRepository.findUserByUserName(request.getUserName()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);

        }
        var user = userMapper.updateUser(userRepository.findUserByUserName(request.getUserName()).get(), request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public UserDTOResponse updateRoleToUser(UpdateRoleToUserRequest request) {
        var user = userRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserDTOResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        return userMapper.toUserResponse(userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return "Success";

    }

}
