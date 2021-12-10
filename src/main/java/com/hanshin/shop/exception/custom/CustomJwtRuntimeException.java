package com.hanshin.shop.exception.custom;

import com.hanshin.shop.exception.ErrorCode;

public class CustomJwtRuntimeException extends Throwable {

    public CustomJwtRuntimeException(){
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomJwtRuntimeException(Exception ex){
        super(ex);
    }
}
