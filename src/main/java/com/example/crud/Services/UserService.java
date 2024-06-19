package com.example.crud.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.Request.UserDTO;
import com.example.crud.DTO.Response.UserDTOResponse;
import com.example.crud.Entity.User;
import com.example.crud.Mapper.UserMapper;
import com.example.crud.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDTOResponse toUserDTOResponse(User user) {
        return UserDTOResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .build();
    }

    public List<UserDTOResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTOResponse> userDTOResponses = new ArrayList<>();
        for (User user : users) {
            userDTOResponses.add(toUserDTOResponse(user));

        }
        return userDTOResponses;

    }

    public User createUser(UserDTO request) {
        if (userRepository.findUserByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("Tài khoản đã tồn tại");

        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        return userRepository.save(user);
    }

    public void updateUser(UserDTO request) {
        Optional<User> exUser = userRepository.findById(request.getId());
        if (exUser.isPresent()) {
            User user = exUser.get();
            user.setPassWord(request.getPassWord());
            userRepository.save(user);
        }

    }

    // viết tạm
    public boolean checkPassword(User user, UserDTO request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        return passwordEncoder.matches(request.getPassWord(), user.getPassWord());
    }

}
