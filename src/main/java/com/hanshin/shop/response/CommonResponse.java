package com.hanshin.shop.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonResponse {
    private String message;
    private int status;
    private String code;
}
