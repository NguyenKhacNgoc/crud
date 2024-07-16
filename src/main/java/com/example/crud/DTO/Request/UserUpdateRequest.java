package com.example.crud.DTO.Request;

import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String userName;
    @Size(min = 8, message = "PASSWORD_VALID")
    String passWord;
    String fullName;
    Set<String> roles;

}
