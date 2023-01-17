package com.jorados.diary.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class SeongjinException extends RuntimeException{
    public SeongjinException(String message) {
        super(message);
    }
    public SeongjinException(String message, Throwable cause) {
        super(message, cause);
    }
    private final Map<String,String> validation = new HashMap<>();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    }
    public abstract int getStatusCode();
}
