package com.example.crud.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "Tài khoản không tồn tại", HttpStatus.BAD_REQUEST),
    PASSWORD_VALID(1003, "Mật khẩu phải chứa ít nhất 8 ký tự", HttpStatus.BAD_REQUEST),
    WSP_NOT_EXISTED(1005, "WorkSpace không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1006, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    ROLE_NOT_EXISTED(1007, "Role không tồn tại", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1004, "UnAuthenticated", HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
