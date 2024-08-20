package com.example.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "UNCATEGORIZED EXCEPTION",
            HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(HttpStatus.BAD_REQUEST.value(), "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "Tài khoản không tồn tại", HttpStatus.NOT_FOUND),
    PASSWORD_VALID(HttpStatus.BAD_REQUEST.value(), "Mật khẩu phải chứa ít nhất 8 ký tự", HttpStatus.BAD_REQUEST),
    WSP_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "WorkSpace không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    ROLE_NOT_EXISTED(1007, "Role không tồn tại", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "UnAuthenticated", HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
