package com.example.crud.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.crud.DTO.Response.ApiResponse;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppExeption(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        @SuppressWarnings("rawtypes")
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    // bắt lỗi ngoại lệ
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handlingExeption(Exception exception) {
        @SuppressWarnings("rawtypes")
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // bắt lỗi Access Denied
    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<?> handlingAccessDenied(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        @SuppressWarnings("rawtypes")
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @SuppressWarnings("null")
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        @SuppressWarnings("rawtypes")
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}