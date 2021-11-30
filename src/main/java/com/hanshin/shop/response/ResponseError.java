package com.hanshin.shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
public class ResponseError {

    // HttpStatus 코드의 데이터, 여러에러의 복합이면 가장 일반적인 코드 사용
    // the HTTP status code applicable to this problem, expressed as a string value. ( When a server encounters multiple problems for a single request, the most generally applicable HTTP error code SHOULD be used in the response )
    private String status;

    // 각각의 Application에서 사용할 별도의 코드 : an application-specific error code, expressed as a string value
    private String code;

    // 에러의 제목 : a short, human-readable summary of the problem
    private String title;

    // 에러의 상세내용 : a human-readable explanation specific to this occurrence of the problem. Like title, this field’s value can be localized.
    private String detail;

    public void setError(Exception error) {
    }
}