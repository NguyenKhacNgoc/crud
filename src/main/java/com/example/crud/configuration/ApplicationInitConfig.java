package com.example.crud.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.crud.Entity.User;
import com.example.crud.Enums.Role;
import com.example.crud.Repository.UserRepository;

@Configuration
public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return arg -> {
            if (userRepository.findUserByUserName("admin").isEmpty()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                HashSet<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());
                userRepository.save(User.builder()
                        .userName("admin")
                        .passWord(passwordEncoder.encode("admin"))
                        // .role(roles)
                        .build());
            }

        };
    }

}
