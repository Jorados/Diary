package com.jorados.diary.response;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message,Map<String,String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName,errorMessage);
    }
}
