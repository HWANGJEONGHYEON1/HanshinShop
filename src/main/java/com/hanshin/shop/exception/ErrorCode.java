package com.hanshin.shop.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    AUTHENTICATION_FAILED(401, "AUTH_001", "AUTHENTICATION_FAILED."),
    LOGIN_FAILED(401, "AUTH_002", "LOGIN_FAILED."),
    ATTACHMENT_NOT_EXIST(400, "ATTACH_001", "첨부파일이 존재하지 않습니다."),
    INVALID_JWT_TOKEN(401, "AUTH003", "INVALID_JWT_TOKEN.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}