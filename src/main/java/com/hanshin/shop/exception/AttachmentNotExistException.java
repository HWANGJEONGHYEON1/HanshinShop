package com.hanshin.shop.exception;

public class AttachmentNotExistException extends RuntimeException {
    private final static String message = "첨부파일이 존재하지 않습니다.";

    public AttachmentNotExistException() {
        super(message);
    }
}
