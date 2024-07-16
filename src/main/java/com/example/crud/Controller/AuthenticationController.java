package com.example.crud.Controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.Request.AuthenticationRequest;
import com.example.crud.DTO.Response.ApiResponse;
import com.example.crud.DTO.Response.AuthenticationResponse;
import com.example.crud.Services.AuthenticateServices;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticateServices authenticateServices;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticateServices.authenticate(request));
        return apiResponse;
    }

    @SuppressWarnings("unchecked")
    @PostMapping("getUserNameFromToken")
    public ApiResponse<String> getUserNameFromToken(@RequestHeader("Authorization") String authorization)
            throws JOSEException, ParseException {
        String token = authorization.substring(7);
        @SuppressWarnings("rawtypes")
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticateServices.getUserNameFromToken(token));
        return apiResponse;
    }

}
