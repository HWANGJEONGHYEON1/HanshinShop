package com.hanshin.shop.exception.custom;

import com.hanshin.shop.exception.ErrorCode;

public class AttachmentNotExistException extends RuntimeException {

    public AttachmentNotExistException() {
        super(ErrorCode.ATTACHMENT_NOT_EXIST.getMessage());
    }
}
