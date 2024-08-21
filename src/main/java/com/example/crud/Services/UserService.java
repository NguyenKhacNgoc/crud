package com.example.crud.services;

import java.util.Collections;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.crud.dto.request.UserCreationRequest;
import com.example.crud.dto.request.UserUpdateRequest;
import com.example.crud.dto.response.PageResponse;
import com.example.crud.dto.response.UserResponse;
import com.example.crud.entity.User;
import com.example.crud.exception.AppException;
import com.example.crud.exception.ErrorCode;
import com.example.crud.mapper.UserMapper;
import com.example.crud.repository.UserRepository;

import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    @NonFinal
    @Value("${keycloak.realm}")
    String realm;
    Keycloak keycloak;

    public void createUser(UserCreationRequest request) {

        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(new CredentialRepresentation() {
            {
                setType(CredentialRepresentation.PASSWORD);
                setValue(request.getPassword());
                setTemporary(false);
            }
        }));
        Response response = usersResource.create(user);
        if (response.getStatus() == 201) {
            String pathUri = response.getLocation().getPath();
            int lastSlashIndex = pathUri.lastIndexOf("/");
            String userId = pathUri.substring(lastSlashIndex + 1);

            User userdb = userMapper.toUser(request);
            userdb.setId(userId);
            userRepository.save(userdb);
            log.info("response " + userId);

        } else if (response.getStatus() == 409) {
            throw new AppException(ErrorCode.USER_EXISTED);

        }

    }

    public void sendVerificationEmail(String userId) {
        keycloak.realm(realm).users().get(userId).sendVerifyEmail();
    }

    public PageResponse<UserResponse> getAllUser(int page, int size) {
        Pageable userPage = PageRequest.of(page - 1, size);
        var pageData = userRepository.findAll(userPage);
        return PageResponse.<UserResponse>builder().currentPage(page).pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages()).totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(userMapper::toUserResponse).toList()).build();

    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {

        if (id == null) {
            id = SecurityContextHolder.getContext().getAuthentication().getName();

        }

        var user = keycloak.realm(realm).users().get(id).toRepresentation();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        keycloak.realm(realm).users().get(id).update(user);
        return userMapper.toUserResponse(
                userRepository.save(userMapper.updateUser(userRepository.findById(id).get(), request)));

    }

    public void deleteUser(String id) {

        var user = keycloak.realm(realm).users().get(id);
        user.remove();
        userRepository.deleteById(id);

    }

}
