package com.jorados.diary.controller;

import com.jorados.diary.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    //@Valid 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //@Valid로 인한 메소드 에러가 발생했을때만 이 컨트롤러가 에러를 잡아주게 끔
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        //ErrorResponse에 MethodArgumentNotValidException에러(@NotBlank 필드에러)를 추가해서 리턴한다.
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage()); //addValidation(어떤필드,프로퍼티에있는 메시지)
        }
        return response;
    }
}
