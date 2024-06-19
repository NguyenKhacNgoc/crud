package com.example.crud.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.Request.UserDTO;
import com.example.crud.Entity.User;
import com.example.crud.Repository.UserRepository;
import com.example.crud.Services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser() {
        if (userService.getAllUser().isEmpty()) {
            return ResponseEntity.status(404).body("Rỗng");
        }
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody UserDTO request) {
        return userService.createUser(request);

    }

    @PutMapping("updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO request) {
        if (userRepository.findById(request.getId()).isPresent()) {
            userService.updateUser(request);
            return ResponseEntity.ok().body("Cập nhật thành công");
        }
        return ResponseEntity.badRequest().body("Người dùng không tồn tại");

    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO request) {
        Optional<User> exU = userRepository.findById(request.getId());
        if (exU.isPresent()) {
            userRepository.delete(exU.get());
            return ResponseEntity.ok().body("Đã xoá");
        }
        return ResponseEntity.badRequest().body("Người dùng không tồn tại");
    }

    @PostMapping("checkPassword")
    public ResponseEntity<?> checkPassword(@RequestBody UserDTO request) {
        Optional<User> exU = userRepository.findUserByUserName(request.getUserName());
        if (exU.isPresent()) {
            boolean isValid = userService.checkPassword(exU.get(), request);
            if (isValid)
                return ResponseEntity.ok(isValid);
            else
                return ResponseEntity.badRequest().body(isValid);
        }
        return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
    }

}
